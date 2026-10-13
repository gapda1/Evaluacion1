
package udg.cuvalles.evaluacion1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import udg.cuvalles.evaluacion1.ui.theme.Evaluacion1Theme

val personas = listOf(
    Data(R.drawable.persona05, "Juan Perez", "Programador Web", 84),
    Data(R.drawable.persona04, "Kenia Os", "Cantante", 10000),
    Data(R.drawable.persona03, "Juan de Dios Pantoja", "Youtuber", 10),
    Data(R.drawable.persona01, "Kimberly Loaiza", "Youtuber", 15),
    Data(R.drawable.persona02, "Peso Pluma", "Cantante", 10000),
    Data(R.drawable.persona05, "Carlos Lopez", "Diseñador", 150),
    Data(R.drawable.persona04, "Ana Garcia", "Influencer", 250),
    Data(R.drawable.persona03, "Pedro Ramirez", "Streamer", 500),
    Data(R.drawable.persona01, "Maria Torres", "Cantante", 700),
    Data(R.drawable.persona02, "Luis Martinez", "Fotógrafo", 300)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Evaluacion1Theme() {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MyColum(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun MyColum(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {


        Text(
            text = "Tarjeta",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 12.dp,
                bottom = 4.dp
            )
        )

        TarjetaPersona(personas[0])

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {


            item {
                Text(
                    text = "Lista Horizontal - LazyRow",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(personas.take(8)) { persona ->

                        Card(
                            modifier = Modifier.width(150.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Image(
                                    painter = painterResource(
                                        id = persona.foto
                                    ),
                                    contentDescription = persona.nombre,
                                    modifier = Modifier.size(90.dp)
                                )

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Text(
                                    text = persona.nombre,
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Text(
                                    text = persona.description,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Text(
                                    text = "Likes: ${persona.likes}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }


            item {
                Text(
                    text = "Lista Vertical - LazyColumn",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }


            items(
                items = personas,
                key = { it.nombre }
            ) { persona ->

                TarjetaPersona(persona)
            }
        }
    }
}


@Composable
fun TarjetaPersona(persona: Data) {

    var mostrarTexto by remember(persona.nombre) {
        mutableStateOf(false)
    }

    var seleccionado by remember(persona.nombre) {
        mutableStateOf(false)
    }


    val colorCard by animateColorAsState(
        targetValue = if (seleccionado) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(
            durationMillis = 500
        ),
        label = "colorCard"
    )

    Card(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxWidth()
            .clickable {
                seleccionado = !seleccionado
            },
        colors = CardDefaults.cardColors(
            containerColor = colorCard
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = persona.foto
                ),
                contentDescription = persona.nombre,
                modifier = Modifier.size(80.dp)
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = persona.nombre,
                    style = MaterialTheme.typography.titleMedium
                )


                AnimatedVisibility(
                    visible = mostrarTexto
                ) {
                    Column {

                        Text(
                            text = persona.description,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Likes: ${persona.likes}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Button(
                    onClick = {
                        mostrarTexto = !mostrarTexto
                    },
                    modifier = Modifier.padding(
                        top = 8.dp
                    )
                ) {

                    Text(
                        text = if (mostrarTexto) {
                            "Ocultar"
                        } else {
                            "Mostrar"
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Evaluacion1Theme() {
        MyColum()
    }
}
