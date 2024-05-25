# Picsum Android application

This application displays images retrieved from the Picsum API listing.

## Architecture of the application

This application is made of a UI and a data module

### Data layer / `data` module / `com.resy.picsum.data` package name

This module is the module containing the data, which are the models, the datasource contracts, and
the repositories implementations.

### Framwork layer / `framework` module / `com.resu.picsum.framework` package name

This module is the module which will contain implementation of the contracts in the data module.

### Domain layer / `domain` module / `com.resy.picsum.domain` package name

This module is the module containing the use cases of the application.

### UI layer / `app` module / `com.resy.picsum.android` package name

This module is the module containing the UI and the elements of the running application. The name
of the package has be chosen to be distinct from other modules, and to make sense as an application
package name in the store.

#### Single activity architecture

The architecture will be based on a single Activity, and navigating between view will be performed
with a Navigation component.

## Features of the applications

### MVP

The MVP (minimum viable product) is the minimal goal to achive. If this is not achieve, application
can be considered as not released :

* Get the list of pictures from the API
* Display the picture from the Network

### Goal

The goal is what we will tend to achieve for this project, and what we will commit on.

* Use Room cache for the list of pictures
* Use Image cache for displaying pictures

### Optional

This is the list of features that would be great to have if we have time to develop it

* Background service to download pictures, for example pre-download them if the user is using Wi-fi
network
* Having an LRU cache logic, in order to maintain a reasonable size for the cache of the application