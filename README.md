# Viikkotehtävä 1

## Datamalli
Sovelluksessa tehtiin Task data class (id, title, description, priority, dueDate, done)
5 kappaletta mock-dataa (tiedostossa MockData.kt)

## Kotlin-funktiot ja napit niiden käyttöön
addTask
toggleDone
filterByDone
sortByDueDate

## UI
HomeScreen näyttää otsikon ja tehtävälistan

# Viikkotehtävä 2

## Compose-tilanhallinta
Jetpack Compose on Googlen moderni deklaratiivinen käyttöliittymäkirjasto Android-sovelluksille, jossa käyttöliittymä kuvataan aina nykyisen tilan perusteella.
UI reagoi aina automaattisesti tilan muutoksiin, eikä näkymää tarvitse päivittää käsin.

## Miksi viewmodel on parempi kuin pelkkä remember
remember säilyttää tilan vain Composable-funktion ajan ja se voi nollautua esimerkiksi ruudun käännöksissä tai näkymän uudelleenluonnissa. Se soveltuu hyvin lyhytikäiseen UI-tilaan kuten tekstikentän sisältöön.
ViewModel on suunniteltu säilyttämään sovelluksen tila elinkaaren yli. Se erottaa käyttöliittymän ja sovelluslogiikan toisistaan, tekee koodista selkeämmän ja helpommin testattavan. Siksi sovelluksen varsinaista tilaa kuten tehtävälistaa hallitaan ViewModelissa.

# Viikkotehtävä 3

## Selitä MVVM, miksi se on hyödyllinen Compose-sovelluksissa.

MVVM (Model–View–ViewModel) on arkkitehtuurimalli, joka jakaa sovelluksen kolmeen selkeään kerrokseen:
- Model sisältää sovelluksen datan ja tietorakenteet
- View vastaa käyttöliittymästä 
- ViewModel sisältää sovelluslogiikan ja tilanhallinnan sekä toimii välikätenä View- ja Model-kerrosten välillä

MVVM on erityisen hyödyllinen Compose-sovelluksissa, koska Compose perustuu reaktiiviseen tilanhallintaan. Kun ViewModel hallitsee sovelluksen tilaa ja UI vain kuuntelee sitä, käyttöliittymä päivittyy automaattisesti ilman erillisiä päivityskutsuja.

## Kerro miten StateFlow toimii.

StateFlow on reaktiivinen tietovirta, joka säilyttää aina viimeisimmän tilan. Kun ViewModel päivittää StateFlown arvoa, kaikki sitä kuuntelevat käyttöliittymäkomponentit saavat muutoksen automaattisesti. Jetpack Compose käyttää collectAsState()-funktiota kuunnellakseen StateFlowta, jolloin käyttöliittymä päivittyy heti tilan muuttuessa ilman erillistä päivityslogiikkaa.
