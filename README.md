# BarbrasBook

## Welcome!

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

## Things of note
The main recycler view contains a swipe down to refresh feature  <br />
You wont regret sending Barbra a heart in the article fragment

# THANKS :)
