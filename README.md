Mobile Take Home
===

This project is an assignment by Guestlogix, which you can find them as I forked their repository. 
The original README.md file is available as ASSIGNMENT_README.md.

Modules
---
This project implements the Clean Architecture. As you can read from [https://blog.cleancoder.com](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
the Clean Architecture has an onion shape structure like this

![clean-architecture](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

In this project we manage it like this

- `domain` module: includes the `Entity` and `Use Cases`.
- `data` module: includes the `Gateways`, `DB`, `External Interfaces` and `Device`.
- `presentation_map` module: includes the `MVVM` architectural pattern, which is the `UI` and `Presenters` in the diagram above. 
This module is keeping codes and resources of main activity, which we call it map activity here. 
- `presentation_path_selector` module: the same as `presentation_map` module, but holding codes and resources of an 
Android activity to handle path selection. This Activity could be a fragment in the map activity, but to keep navigation 
easier and persist pages stack while relaunching the app from recent button of the device, I decided to use an activity here.
- `app` module: integrates all classes and assemble the apk file. Mainly the modules and providers of Dagger framework live here.

By applying the DIP, dependency inversion principle, `data`, `presentation_path_selector` and `presentation_map` modules 
depends on the `domain` module as the heart of the app. Also, the `app` module have to depend on every other modules.
