# Picsum Android application

![Android](https://github.com/gahfy/picsum/actions/workflows/android.yml/badge.svg)

Picsum is an Android application that lists images from the
[Picsum](https://picsum.photos/) REST API and displays each image with a
different layout depending on the image dimensions.

<img src="doc/img/demo_app.gif" alt="demo" width="200"/>

The apk of the last version (1.0) of the application can be found in
[Version 1.0](releases/tag/v1.0)

## Features

The application consists of two screens: one displaying the list of images and
another showing the details of a selected image.

### Screen orientation

> The most common orientation for an Android app is portrait, since that's how
most phones are held. While portrait is good for phones, it's terrible for
laptops and tablets, where landscape is preferred. To get the best results for
your app, consider supporting both orientations.
[Android Window Management - Screen Orientation](https://developer.android.com/topic/arc/window-management#screen_orientation)

For this reason, even if the constraints state that only phone portrait needs
to be supported, both landscape and portrait orientation are supported. However,
layout is optimized only for portrait, and landscape mode is only _Supported_.

### Image list

The list is loaded and then displayed to the user following this loading
mechanism:

![Loading images](doc/img/image_list_feature_diagram.svg)

#### Error screen

<img src="doc/img/image_list_error_screen.png" alt="error screen" width="200"/>

* When the user clicks the `Try again` button, the loading mechanism described
above is launched again.

#### Image List screen

<img src="doc/img/image_list_success_screen.png" alt="error screen" width="200"/>

The Snackbar visible on this screen appears only when an error occurs while
loading the results from the API.

* When the user clicks the `Try again` button, the loading mechanism described
above is launched again.
* When the user clicks on an image in the list, they are redirected to the
detail screen.

### Image Details

The displayed image is loaded and then shown to the user following this loading
mechanism:

![Loading images](doc/img/loadiing_image_feature_diagram.svg)

#### Error Screen

<img src="doc/img/loading_image_failed.png" alt="error screen" width="200"/>

* When the user clicks the `Try again` button, the loading mechanism described
above is launched again.

#### Detail Screen

| Landscape | Portrait |
| --------- | -------- |
| <img src="doc/img/loading_image_success_landscape.png" alt="error screen" width="200"/> | <img src="doc/img/loading_image_success_portrait.png" alt="error screen" width="200"/> |

The image is displayed at the top of the screen if:

* The image's height is greater than its width

**OR**

* There is not enough height on the screen to display the image and the author's
name below it (this can happen, for example, in portrait mode if the screen is
1000px (width) * 1001px (height) and the image is 1000px (width) by 999px
(height)). Adding the height of the status bar and the author label, there is
not enough height on the screen to display all of them.

Otherwise, the image will be displayed centered vertically in the screen.

#### Common to All Screens

* By clicking the back button, the user will be redirected to the previous
list screen.
* Clicking anywhere on the screen will hide (if visible) or show (if hidden) the
back button.

## Technical

### Compiling and running the application

In order to compile and run the application, just type the following command in
a terminal :

```
./gradlew installDebug
```

### Architecture of the application

The application follows an MVVM architecture, with a Single state per screen. It
is a Single Activity application, with a `Navigation` component in order to
navigate between screens.

#### Modules and packages of the application

The application is made around four modules, which are detailed below

* <img style="vertical-align:middle" src="doc/img/green_folder.svg" /> **app** `com.resy.picsum.android` _(Android module)_<br />
_The app module essentially contains the UI of the application_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.component**<br />
  _The component package contains all Compose components which are not screens_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.image**<br />
  _The image package contains everything (screens, states and ViewModels)
  related to the details of an image_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.imagelist**<br />
  _The imagelist package contains everything (screens, states and ViewModels)
  related the list of images_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.model**<br />
  _The model package contains data classes which are used only in the UI package
  (like events)_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.navigation**<br />
    _The navigation package contains all Compose components related to
    navigation (Navigation component and screen destinations)_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **ui.theme**<br />
    _The theme package contains everything (colors, fonts, ...) related to
    Material3 theming of the app_
* <img style="vertical-align:middle" src="doc/img/blue_folder.svg" /> **data** `com.resy.picsum.data` _(Kotlin module)_<br />
_The data module essentially contains the application data and the buisness logic_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **datasource**<br />
  _The datasource package contains the interface contracts for accessing the
  datasources which are used in the application_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **model**<br />
  _The model package contains all the data classes which are used as models in
  the application_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **repository**<br />
  _The repository package contains all the interface contracts as well as their
  implementation using the datasource contracts_
* <img style="vertical-align:middle" src="doc/img/blue_folder.svg" /> **domain** `com.resy.picsum.domain` _(Kotlin module)_<br />
_This module serves as man in the middle between the data and the UI, allowing
to hold complex business logics or ones that are used in many ViewModels_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **usecase**<br />
  _The usecase package contains all the interface contracts for use cases which
  are used in the application as well as their implementation using the
  repository contracts._
* <img style="vertical-align:middle" src="doc/img/green_folder.svg" /> **framework** `com.resy.picsum.framework` _(Android module)_<br />
_This module holds everything which is related to Android framework but which is
not part of the UI of the application_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **android**<br />
  _This package contains Android specific logic, like providing resources for
  example._
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **api**<br />
  _This package contains the API models and service contracts_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **database**<br />
  _This package contains models, DAO and definition of the database of the
  application_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **datasource**<br />
  _This package contains the implementation of the datasource contracts which
  can be found in the `data` module_
  * <img style="vertical-align:middle" src="doc/img/grey_folder.svg" /> **di**<br />
  _This package contains all the dependency injections modules of the
  application_

### About not using Coil

Coil is definitely the library to use with Compose in order to load images from
a remote source, and would have provided better performance, including cache
size management, ... than the actual ones with a custom downloader.

However, Coil is based on the use of the `AsyncImage` component, which is by
definition **a part of the UI which would come from a third-party API**.

Because the constraints of the project state that _"No third-party libraries for
building the UI", we have decided to come with a custom solution instead of the
recommended Coil library.

### Unit Tests

The project contains a lot of unit tests. Parts of those unit tests which can be
found in [ImageViewModelTest.kt](app/src/test/java/com/resy/picsum/android/ui/image/ImageViewModelTest.kt)
are `testPortraitForVeryLongScreen()`, `testLandscapeForVeryLongScreen()` and
`testLandscapeForVeryShortScreen()` which are testing that the Landscape/Portrait
logic works as expected.