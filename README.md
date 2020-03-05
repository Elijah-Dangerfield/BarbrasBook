# BarbrasBook

## Welcome!

#### This application was built as part of my hiring process at Takl On-Demand Services as well as an exploration of mordern Android practices. I used [this](newsapi.org) api in order to pull news articles on Barbra Streisand for everyone to enjoy :)

I also wrote a small article about how to emulate the iOS "prefersBigTitles" attribute as I did in this app. You can find that [here](https://medium.com/better-programming/how-to-emulate-ios-prefersbigtitles-in-android-36c24448f5ff)

![](https://firebasestorage.googleapis.com/v0/b/github-images.appspot.com/o/barbs.png?alt=media&token=7da1f306-e44f-42a9-acd0-81bb508c5f40)

## Architecture
The architecture used in the app follows model-view-viewModel(MVVM) with a repostiory layer using Retrofit for networking.
I used a network bound resource class in order to keep the room database as a single source of truth. 
I used Jetpack's navigation component to keep the app simple and lightweight with fragments. 

## Folders

### model/
This folder contains the data classes used to model the article data used

### api/
This folder contains all the sweet sauce for Retrofit 

### util/
This holder contains a helper class that lets me resize text views as well as come Kotlin extension functions to make my life easier

### ui/
Contains all views(fragments) paired with view models




## Tech Stack
Jetpack Components: navigation, room database, live data  <br />
Retrofit (networking library) <br />
Kotlin <br />
Koin (dependency injection) <br />
lottie (animation library) <br />




## Things of note
The main recycler view contains a swipe down to refresh feature  <br />
You wont regret sending Barbra a heart in the article fragment

# THANKS :)
