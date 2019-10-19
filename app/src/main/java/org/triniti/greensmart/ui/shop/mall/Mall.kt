package org.triniti.greensmart.ui.shop.mall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.layout_f_mall.*
import org.triniti.greensmart.R
import org.triniti.greensmart.data.pojos.Product
import org.triniti.greensmart.data.pojos.Shop
import org.triniti.greensmart.utilities.addMenu
import org.triniti.greensmart.utilities.calculateNoOfColumns

class Mall : Fragment() {
    private val shops: MutableList<MallItems> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_f_mall, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbMall.setupWithNavController(findNavController())
        addMenu(tbMall)

        shops.clear()
        shops.add(
            MallItems(
                Shop(
                    "Brookside",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                    , it = listOf(
                        Product(
                            1,
                            2,
                            "Brook Vanilla",
                            "20",
                            15,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS5sLiT9MeVgSamg31LHGvRtSSa_1oU6fmBXpZuw6oUsxaE-TrH"
                        ),

                        Product(
                            1,
                            2,
                            "Brook yorghurt",
                            "500",
                            270,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRw2yTOZVIl2NQ_3TUXcE2zwWUry-kdF74zCPCs_mjBv__EmKnI"
                        ),

                        Product(
                            1,
                            2,
                            "Brookside strawberry",
                            "90",
                            55,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTmyDKaEfvYcAbZ1hyUOTWXPEdw2jniItJbFbVm_gQ1MBxoyXa3"
                        ),

                        Product(
                            1,
                            2,
                            "Delamere yoghurt",
                            "30",
                            20,
                            "https://www.masoko.com/media/catalog/product/cache/image/700x700/e9c3970ab036de70892d86c6d221abfe/d/e/delamere-pear-caramel_1.png"
                        )
                    )
                ), findNavController()
            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Coke",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                    , it = listOf(
                        Product(
                            1,
                            2,
                            "Coke zero 1l",
                            "20",
                            15,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR1zC_uMAkFAdXqBumvkflP-8tuUTeCUncSS2qq41lZrpdOA5bx"
                        ),

                        Product(
                            1,
                            2,
                            "Coca cola",
                            "500",
                            270,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTC5p_7NfGDc1_utFdM-rALS5ruZR0mUJMRr4j3IZa8kbvsT-_R"
                        ),

                        Product(
                            1,
                            2,
                            "Fanta",
                            "90",
                            55,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTIeqz1sB3-2jcBijiaO9tMWudT3ds5LyYcjVTSuTeYIWvI0z29"
                        ),

                        Product(
                            1,
                            2,
                            "Sprite",
                            "30",
                            20,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTOLlh0qfrrFjCqMxl0yI44C9dLxA1RoQX7qXlgxH-C-l_Y1vPR"
                        )
                    )
                ), findNavController()

            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Foods",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                    , it = listOf(
                        Product(
                            1,
                            2,
                            "1 Kg Tomato",
                            "90",
                            55,
                            "https://www.levarht.com/wp-content/uploads/2018/05/55-tomato.png"
                        ),

                        Product(
                            1,
                            2,
                            "1 Kg Onions",
                            "500",
                            270,
                            "https://www.kroger.com/product/images/large/front/0000000004093"
                        ),

                        Product(
                            1,
                            2,
                            "500g Avocado",
                            "30",
                            20,
                            "https://organicfoodsandcafe.com/wp-content/uploads/2015/09/4521-pg.png"
                        ),

                        Product(
                            1,
                            2,
                            "1 Kg Onions",
                            "500",
                            270,
                            "https://www.kroger.com/product/images/large/front/0000000004093"
                        )
                    )
                ), findNavController()

            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Kevian",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTR0jEJ7drjnHBKbhXYMlhgsJQYgfyRD995tzmrOmIzYuQLrYsY"
                    , it = listOf(
                        Product(
                            1,
                            2,
                            "Pick and peel",
                            "90",
                            55,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNrCsSb-uXkoVrd5paCE5hCalbIuCMPd7YlhcgF4hkpRlxy7P9"
                        ),

                        Product(
                            1,
                            2,
                            "Afia",
                            "20",
                            15,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS6tunI0UQZRRTSwto-5LjY1R9KlG84uECewFKUHTJ9N956vZm"
                        ),

                        Product(
                            1,
                            2,
                            "Afia mango",
                            "500",
                            270,
                            "https://www.shasha.co.ke/media/catalog/afiaorange.png"
                        ),

                        Product(
                            1,
                            2,
                            "Pick and peel orange",
                            "30",
                            20,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSMamzQdaeLqMm_Ppuz1bTPEb9tpnSIAhu-tPrMLFav7QnwANpg"
                        )
                    )
                ), findNavController()

            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Shop",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                    ,it =  listOf<Product>()
                )
                , findNavController()
            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Shop",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                )
                , findNavController()
            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Shop",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                )
                , findNavController()
            )
        )
        shops.add(
            MallItems(
                Shop(
                    "Shop",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSoekv8G1RtduYce36n3wPqLlRQSvxk7odANjSalYmN7yeiWBSY"
                )
                , findNavController()
            )
        )

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(shops)
        }

        rvMall.apply {
            adapter = groupAdapter
            layoutManager = GridLayoutManager(context, calculateNoOfColumns(context, 180f))
        }
    }
}