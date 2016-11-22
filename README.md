# Voyage
bon voyage with this amazing app!

### Itinerary Planner
This feature helps travellers make the best use of their time while on holiday. Users simply select the attractions they wish to visit and enter a transport budget, and the app determines the best route to take.

Suppose the user selects Singapore Flyer and Zoo, and enters a budget of $20. It is assumed that the start and end points of the journey are Marina Bay Sands.

There are two algorithms to find the best route, both in Route.java: getBestRoute, a brute-force method, and getApproxBestRoute, a more efficient algorithm that obtains a reasonably fast route. 

#### getBestRoute

1. Selected attractions are permutated to generate a list of all possible sub-routes 
> All sub-routes: [[MBS, Flyer, Zoo, MBS], [MBS, Zoo, Flyer, MBS]]

2. For each sub-route, the 3 transport methods – taxi, public transport and walking – are permutated and the total time and cost calculated for each permutation. The transport permutation with the shortest time and with total cost within the budget is selected.
> For [MBS, Flyer, Zoo, MBS], best transport permutation is [taxi, public transport, public transport] with a total cost of $6.99 and time 174min  
> For [MBS, Zoo, Flyer, MBS], best transport permutation is [public transport, public transport, taxi] with a total cost of $8.24 and time 177min

3. The sub-route with the lowest total time from step 2 is selected as the best route
> Best route: sub-route [MBS, Flyer, Zoo, MBS], transport [taxi, public transport, public transport], total cost of $6.99, total time 174min

#### getApproxBestRoute

1. Selected attractions are ordered based on a pre-existing ordering of all attractions (since the positions of all attractions are known, a reasonably good order of visiting them can be determined)
> Selected sub-route is [MBS, Flyer, Zoo, MBS]

2. For the ith transport segment out of n, taxi is selected if total cost is within budget*i/n, else public transport is selected
> 1st segment (MBS to Flyer): total cost is $3.22 < $20x1/3, so taxi is chosen  
> 2nd segment (Flyer to Zoo): total cost is $3.22+$18.18 > $20x2/3, so public transport is chosen  
> 3rd segment (Zoo to MBS): total cost is $3.22+$1.89+$22.48 > $20, so public transport is chosen

3. The approximate best route has been found
> Best route: sub-route [MBS, Flyer, Zoo, MBS], transport [taxi, public transport, public transport], total cost of $6.99, total time 174min

getBestRoute has a time complexity of Θ(n!n!) since both attractions and transport methods must be permutated, while getApproxBestRoute has a time complexity of Θ(n) since a constant number of operations is performed for each attraction.

### Tourist Attraction Locator

This feature allows user to input a tourist attraction and returns the location on google maps. 

Methods:

1. openMap()
> This method is called whenever the "Get Location" button is pressed. It makes a reference to the input in the edit text box, converts it to lower cases and corrects typoes using the corrector method. Thereafter, an intent to Google maps is created and the corrected String would be passed into it.

2. clear()
> Clears the edit text box.

3. corrector()
> Utilises regex to correct typos. The regex requires the starting two letters of every key word to be correct and provides a buffer region for errors i.e \\w{a,b} where a is 1. -1 the remaining letters in that word and b is +2 that number. Eg user can key in "barraaage" and still get an output of "Marina Barrage".


### Singlish Dictionary
Singlish is a unique language which takes elements from various Chinese dialects, Malay and Tamil to create a language that is used by Singaporeans. This is often a great challenge for tourists in Singapore and this attempts to bridge that gap. 

A [web-scraper](https://github.com/bowsplinter/chisel) is used to get data from https://en.wikipedia.org/wiki/Singlish_vocabulary A HTTP request is made to the wikipedia and the DOM elements analyzed. Since the source has different types of tables with different layouts, the table header for each table is analyzed to understand what sort of data the table contains. The data is then sorted accordingly and passed to the android app through a txt file.

This data is populated into the app's SQLite database and which allows for powerful query. This is only done the first time the user opens the app. Subsequent times, a check is made to see if any data has change and updates accordingly.

The dictionary page contains two parts. 

1. A list page with a search bar and all the words in the dictionary.

2. A details page for each word which shows the specific data for the word.
> Since each word has a different combination of information (word, origin, meaning, usage) from the source, only the relevant header and data is shown based on which information the word contains.
