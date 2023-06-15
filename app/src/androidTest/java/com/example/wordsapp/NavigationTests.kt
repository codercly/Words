package com.example.wordsapp

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertEquals
import org.junit.Test
/*Quando testamos o componente de navegação, não é possível ver o dispositivo nem o emulador para navegar.
 Em vez disso, forçamos o controlador de navegação a navegar sem mudar o que é mostrado no dispositivo ou emulador.
 Em seguida, conferimos se o controlador de navegação chegou ao destino correto.*/
class NavigationTests {
    @Test
    fun navigate_to_words_nav_component(){
        //criando uma instancia do teste de controle de navegação
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId = R.style.Theme_Words)
        //declarando explicitamente qual grafico de navegação queremos que o controlador use para o fragmento aberto
        letterListScenario.onFragment{
            fragment -> navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        //acionando o evento que solicita a navegação
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)

    }
}