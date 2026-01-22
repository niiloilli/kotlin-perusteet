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
