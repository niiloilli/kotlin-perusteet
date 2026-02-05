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

# Viikkotehtävä 4
## Selitä lyhyesti:
## Mitä tarkoittaa navigointi Jetpack Composessa.
Jetpack Composen navigointi tarkoittaa näkymien välistä siirtymistä deklaratiivisella tavalla. Navigaatio perustuu tilaan: kun navigaatiotila muuttuu, Compose piirtää automaattisesti oikean ruudun. Erillisiä Activityja tai Fragmentteja ei tarvita jokaiselle näkymälle, vaan koko UI voidaan rakentaa Composable-funktioista.

## Mitä ovat NavHost ja NavController.
NavController vastaa navigaation ohjaamisesta. Sitä käytetään siirtymiseen eri ruutujen välillä (esim. navigate() ja popBackStack()).
NavHost määrittelee, mitkä ruudut sovelluksessa on olemassa ja mikä niistä näytetään kulloinkin. Se yhdistää reitit (routes) niitä vastaaviin Composable-näkymiin.

## Miten sovelluksesi navigaatiorakenne on toteutettu (Home ↔ Calendar).
Sovelluksen navigaatiorakenne
Sovelluksessa on kaksi päänäkymää: HomeScreen ja CalendarScreen
NavHost määrittelee molemmat reitit, ja NavController mahdollistaa siirtymisen näkymien välillä molempiin suuntiin (Home ↔ Calendar). Käyttäjä voi siirtyä HomeScreeniltä CalendarScreenille ja palata takaisin ilman, että sovelluksen tila katoaa.

## Kuvaa arkkitehtuuri:
## Miten MVVM ja navigointi yhdistyvät (yksi ViewModel kahdelle screenille).
MVVM ja navigointi
Sovellus noudattaa MVVM-arkkitehtuuria (Model–View–ViewModel). Navigointi on erotettu näkymistä, mutta molemmat ruudut (HomeScreen ja CalendarScreen) käyttävät samaa ViewModelia. Näin varmistetaan, että liiketoimintalogiikka ja tila pysyvät yhdessä paikassa.

## Miten ViewModelin tila jaetaan kummankin ruudun välillä.
ViewModel sisältää sovelluksen tilan (esim. tehtävälista). Tämä tila on jaettu molempien screenien kesken, koska:
ViewModel luodaan NavHostin yläpuolella
Molemmat ruudut lukevat ja päivittävät samaa tilaa
Kun käyttäjä lisää tai muokkaa tehtävää toisessa näkymässä, muutos näkyy automaattisesti myös toisessa näkymässä Composen reaktiivisen tilanhallinnan ansiosta.

## Selitä lyhyesti:
## Miten CalendarScreen on toteutettu (miten tehtävät ryhmitellään / esitetään kalenterimaisesti).
CalendarScreen esittää tehtävät kalenterimaisessa muodossa. Tehtävät:
Ryhmitellään päivämäärän mukaan
Näytetään listana, jossa jokaisella päivällä on oma otsikkonsa
Tämä tekee tehtävien ajallisesta hahmottamisesta selkeämpää ja helpottaa päivittäisten tehtävien tarkastelua.

## Miten AlertDialog hoitaa addTask ja editTask.
Tehtävien lisääminen ja muokkaaminen hoidetaan AlertDialog-komponentilla:
addTask: avaa tyhjän dialogin, johon käyttäjä syöttää uuden tehtävän tiedot
editTask: avaa saman dialogin, mutta esitäyttää kentät valitun tehtävän tiedoilla
Dialogi käyttää ViewModelin funktioita tallentaakseen muutokset. Näin UI pysyy yksinkertaisena ja kaikki logiikka säilyy ViewModelissa.

