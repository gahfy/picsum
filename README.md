# Picsum Android application

This application displays images retrieved from the Picsum API listing.

## Architecture of the application

This application is made of a UI and a data module

### Data layer / `data` module / `com.resy.picsum.data` package name

This module is the module containing the data, which are the models, the datasource contracts, and
the repositories implementations.

### UI layer / `app` module / `com.resy.picsum.android` package name

This module is the module containing the UI and the elements of the running application. The name
of the package has be chosen to be distinct from other modules, and to make sense as an application
package name in the store.

#### Single activity architecture

The architecture will be based on a single Activity, and navigating between view will be performed
with a Navigation component.