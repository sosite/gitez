#<img width="150" alt="Gitez" src="https://user-images.githubusercontent.com/2025949/45154336-88711f00-b1d7-11e8-97c1-b5a6d17e0fb8.png">
Test app written for recruitment process. Copy, paste, share and smile! :)

## Project assumptions

* Create simple Android application that uses [GitHub API](https://developer.github.com/v3/)

  I decided to use [GitHub GraphQL API V4](https://developer.github.com/v4/) for fun part :)

* Application should allow to search for users and repositories
* Main screen of the app should contain search field and list of search results
* When typing into search field, the list should automatically reload with GitHub users and repositories
* Search results should contain both users and repositories - they can interlace with each other
* Search results should be sorted ascending by result object id's
* When you tap on a user, new screen should be presented. Display username, avatar and number of followers for selected user.
