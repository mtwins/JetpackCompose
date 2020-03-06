package com.example.testjetpacknewapp

import android.gesture.Gesture
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.graphics.vector.VectorAsset
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.text.TextSpan
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    private var vehicleCompatibilityList: VehicleCompatibilityList = VehicleCompatibilityList(listOf())
    private var isListOpen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            createVehicleCompatibilityPage()
        }
    }
    @Composable
    private fun createVehicleCompatibilityPage() {
        MaterialTheme {
            Column(arrangement = Arrangement.Begin) {
                setData()
                createListHeader()
                AppDescription()
                GenerateVehicleCompatiblityList(vehicleCompatibilityList)
            }
        }
    }


    fun setData() {
        var vehicles = arrayListOf<vehicles>()
        vehicles.add(vehicles("test 1", true))
        vehicles.add(vehicles("test 2", false))
        vehicles.add(vehicles("test 3", true))
        vehicles.add(vehicles("test 4", false))
        vehicleCompatibilityList.set(vehicles)
    }

    fun getDataRefresh() {
        var vehicles = arrayListOf<vehicles>()
        vehicles.add(vehicles("test 5", true))
        vehicles.add(vehicles("test 6", false))
        vehicles.add(vehicles("test 7", true))
        vehicles.add(vehicles("test 8", false))
        vehicleCompatibilityList.set(vehicles)
    }

    fun close() {
        vehicleCompatibilityList.set(listOf())

    }
    fun open() {
        setData()
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        createVehicleCompatibilityPage()
    }
    @Preview
    @Composable
    fun ItemListPreview(){
    var vehicles = arrayListOf<vehicles>()
    vehicles.add(vehicles("test 1", true))
    vehicles.add(vehicles("test 2", false))
    vehicles.add(vehicles("test 3", true))
    vehicles.add(vehicles("test 4", false))
    var vehicleList = VehicleCompatibilityList(vehicles)
    GenerateVehicleCompatiblityList(vehicleList)
    }


    @Preview
    @Composable
    fun AppDescription() {
        Row() {
            Text(text = "Some apps may not be compatible with your vehicle’s version of SYNC®. A green check mark indicates a match")
        }

    }
    @Preview
    @Composable
    fun HeaderViewPreview(){
        createListHeader()
    }

    @Preview
    @Composable
    fun ItemViewPreview(){
        createListItem(vehicles("test 1", true))
    }

    @Composable
    fun GenerateVehicleCompatiblityList(vehicleCompatibilityList: VehicleCompatibilityList) {
        Column(
                modifier = Spacing(26.dp)
        ) {
            if (isListOpen) {
                for (vehicle in vehicleCompatibilityList.vehicleList) {
                    Container(alignment= Alignment.TopLeft, modifier = Height(60.dp) wraps Expanded) {
                        Clickable(onClick = {
                            getDataRefresh()
                        }) {
                            createListItem(vehicle)
                            Divider(color = Color.Black)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun createListHeader() {
            Row(arrangement = Arrangement.SpaceBetween) {

                Text("Vehicle Compatibility")
                Clickable(onClick = {
                    if (isListOpen == false) {
                        open()
                        isListOpen = true
                    } else {
                        close()
                        isListOpen = false
                    }
                }) {
                    switchIcon(vehicleCompatibilityList)
                }
            }

    }

    private fun switchIcon(vehicleCompatibilityList: VehicleCompatibilityList) {
        if (vehicleCompatibilityList.vehicleList.isEmpty()) {
            Container(height = 18.dp, width = 18.dp, alignment = Alignment.CenterRight) {
                drawImage(+vectorResource(R.drawable.ic_down_arrow))
            }
        } else {
            Container(height = 18.dp, width = 18.dp, alignment = Alignment.CenterRight) {
                drawImage(+vectorResource(R.drawable.ic_up_arrow))
            }

        }
    }

    @Composable
fun createListItem(vehicle: vehicles) {
    Row(arrangement = Arrangement.Begin){
        if (vehicle.isCompatible) {
            Container(height = 18.dp, width = 18.dp) {
                Clip(shape = RoundedCornerShape(8.dp)) {
                    DrawVector(+vectorResource(R.drawable.ic_baseline_okay))
                }
            }
        } else {
            Container(height = 18.dp, width = 18.dp) {
                DrawVector(+vectorResource(R.drawable.ic_baseline_error_24))
            }
        }
        Text(vehicle.vin)

    }

}
    @Composable
    private fun drawImage(image: VectorAsset) {
        DrawVector(image)
    }

}

@Model
data class VehicleCompatibilityList(
        var vehicleList: List<vehicles>
) {
    fun set(vehicleList: List<vehicles>) {
        this.vehicleList = vehicleList
    }
    fun clear() {
        this.vehicleList = listOf()
    }
}

data class vehicles(
        var vin: String,
        var isCompatible: Boolean
)