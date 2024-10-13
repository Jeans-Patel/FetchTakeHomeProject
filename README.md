Libraries used:
- Jetpack Compose for UI
- Retrofit for network calls
- Hilt for dependency injection
- Kotest for unit test framework
- mockk for mocking

Quick summary of the app:
- I followed data/domain/ui app structure and used MVVM for the UI.
- Unit tests have been made for all classes and I've also included an instrumented test. There wasn't
any navigation in the app so there wasn't much instrumented testing other than basic text checks.
- I used the default UI theme since in a real project I'd be working with the company's design theme
but I applied sufficient padding to still make the list easily readable.


Decisions:
- The requirements were mostly clear but there was some confusion around sorting items. I decided that
"Item 8" should be above "Item 789" in a sorted list so my sorting logic follows this.
- There were no requirements for handling errors so I just displayed a error message when the network
returned an error or empty list.
