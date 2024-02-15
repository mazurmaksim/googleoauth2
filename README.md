
# Setting.html
### Oauth2 Form

This HTML document represents a form for configuring Google OAuth2 application settings.

## Form Fields:

- **Client ID:** Input field for specifying the client ID.
- **Scope:** Input field for defining the scope of access requested by the application.
- **Redirect URI:** Input field for providing the redirect URI where the authorization response will be sent.
- **Response Type:** Input field for specifying the type of response expected from the authorization server.
- **Access Type:** Input field for indicating whether the application requires offline access.
- **Client Secret:** Input field for entering the client secret associated with the client ID.
- **Include Granted Scopes:** Checkbox for indicating whether previously granted scopes should be included in the authorization request.

## Save Settings Button:

- When clicked, the form data is serialized into a JSON object.
- An asynchronous POST request is made to the "/saveSettings" endpoint with the serialized JSON data.
- Upon successful saving of settings (status code 200), an alert is displayed indicating that the settings have been saved, and the form is reset.

## Script:

- The form submission event is intercepted using JavaScript to prevent the default behavior.
- The form data is collected and serialized into a JSON object.
- An XMLHttpRequest is created and configured to send a POST request with the serialized JSON data to the "/saveSettings" endpoint.
- Upon receiving a successful response, an alert is displayed, and the form is reset.

This form allows users to configure OAuth2 settings for their Google application.

### Fetch Data from Google 
To fetch data you need add these permissions to the http://localhost:8080/settings.
scope:   https://www.googleapis.com/auth/contacts
         https://www.googleapis.com/auth/contacts.readonly
         https://www.googleapis.com/auth/directory.readonly
         https://www.googleapis.com/auth/user.addresses.read
         https://www.googleapis.com/auth/user.birthday.read
         https://www.googleapis.com/auth/user.emails.read
         https://www.googleapis.com/auth/user.gender.read 
         https://www.googleapis.com/auth/user.organization.read
         https://www.googleapis.com/auth/user.phonenumbers.read
         https://www.googleapis.com/auth/userinfo.email
         https://www.googleapis.com/auth/userinfo.profile
And those scope add to the Google app admin panel.

### Controller Description

The `Oauth2Controller` class serves as a controller in the application. It handles HTTP requests related to OAuth2 authentication and token management. Below is the description of the main functionalities provided by this controller:

1. **Constructor Injection**:
    - The controller class is constructed with three dependencies: `SettingsRepository`, `TokenHolder`, and `UserRepository`. These dependencies are injected through the constructor, following the dependency injection principle.

2. **Index Endpoint (`/`)**:
    - Handles GET requests to the root URL.
    - Returns the `index.html` view.

3. **Settings Endpoint (`/settings`)**:
    - Handles GET requests to `/settings`.
    - Returns the `settings.html` view.

4. **Save Settings Endpoint (`/saveSettings`)**:
    - Handles POST requests to `/saveSettings`.
    - Saves the application settings received in the request body using the `settingsRepository`.
    - Returns a response entity containing the saved settings.

5. **Authorization Code Endpoint (`/getAuth`)**:
    - Handles GET requests to `/getAuth`.
    - Retrieves the application settings from the `settingsRepository`.
    - Generates an authorization URL based on the settings to initiate the OAuth2 authorization flow.
    - Redirects the user to the generated authorization URL.

6. **Token Endpoint (`/getToken`)**:
    - Handles POST requests to `/getToken`.
    - Retrieves the application settings from the `settingsRepository`.
    - Parses the response JSON containing the authorization code.
    - Requests an access token using the authorization code and the application settings.
    - If the access token is not present, attempts to refresh the token using the refresh token.
    - Retrieves user information from Google using the obtained access token.
    - Saves the user information in the `UserRepository` if the user does not exist.
    - Returns a response entity indicating success.

7. **Success Endpoint (`/success`)**:
    - Handles GET requests to `/success`.
    - Retrieves the access token from the `TokenHolder`.
    - Returns a response entity containing the access token.

This controller orchestrates the OAuth2 authentication flow, handles token management, and interacts with the repository to store and retrieve application settings and user information.

# Authorization Class

This Java class, located in the package `com.authapp.auth.oauth`, provides functionality for OAuth2 authorization and token management.

## Methods:

1. **getToken(AppSettings settings, String code):**
    - Sends a request to obtain an access token using the authorization code.
    - Parameters:
        - `settings`: Application settings containing client ID, client secret, redirect URI, etc.
        - `code`: Authorization code received from the OAuth2 authorization flow.
    - Returns: `Token` object containing access token details.

2. **refreshToken(Token token, AppSettings settings):**
    - Sends a request to refresh the access token using the refresh token.
    - Parameters:
        - `token`: Token object containing the refresh token.
        - `settings`: Application settings containing client ID, client secret, etc.
    - Returns: `Token` object containing the refreshed access token.

3. **getAuthCode(AppSettings settings):**
    - Constructs and returns a URI builder for generating the authorization URL.
    - Parameters:
        - `settings`: Application settings containing client ID, scope, redirect URI, etc.
    - Returns: `UriComponentsBuilder` configured with authorization parameters.

4. **getUserPersonalInfo(Token token):**
    - Retrieves personal information of the user associated with the provided access token.
    - Parameters:
        - `token`: Token object containing the access token.
    - Returns: `Person` object representing the user's personal information.

## Components:

- **Component Annotation:**
    - The class is annotated with `@Component` to indicate that it is a Spring-managed component.

## Dependencies:

- **RestTemplate:**
    - Uses `RestTemplate` for making HTTP requests to OAuth2 endpoints.

## Usage:

- This class facilitates OAuth2 authorization flow and token management within the application.

