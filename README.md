# Codewars

This app fetches the completed challenges of an active code warrior, displays it in a list and provides pagination controls 
for navigating through the data.

## Stack

The app implements the MVVM + Clean Architecture + User Case + Repository Design pattern. Other important 
stack info include:

- [x] Kotlin coroutines, flows and state 
- [x] Retrofit for api calls
- [x] Jetpack compose for user interface
- [x] Dagger-Hilt for dependency injection
- [x] Junit, Hilt Android Testing

## Data

The app retrieve remote data from the free code wars api http://www.codewars.com/api/v1/users/g964/code-challenges/completed.
The data is a list of completed challenges by the user with id g964.

## How to use

The app contains two main screens, the first is a list of all completed code challenges. The data is paginated 
and thus the app provides controls for querying specific pages. 

On clicking an item in the list, the app opens up a screen to show more information about the challenge. For this,
another api call is made to retrieve a more robust info.

You could also type in a page number to jump to a specific page, provided the page exists for the current code war user


<div>
  <img src="https://user-images.githubusercontent.com/9566176/163740624-7168939c-f7e1-4c80-889a-2730dba5254b.png" width="250"  />
  <img src="https://user-images.githubusercontent.com/9566176/163740651-250d3127-f877-4d32-abdf-bfc1d65498e4.png" width="250"  />
</div>
Fig. 1: List Screen in light and dark modes


<div>
<img src="https://user-images.githubusercontent.com/9566176/163740608-5707197a-610e-41a0-98ea-609046ae1e89.png" width="250"  />
<img src="https://user-images.githubusercontent.com/9566176/163740642-8dc96fa3-8103-465d-aabc-94128dd297b0.png" width="250"  />
</div>
Fig. 2: Challenge details screen in light and dark modes

