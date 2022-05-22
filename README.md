# README

## E-COMMERCE PAGE

Welcome to my e-Commerce page.

The database originaly is empty but you could load 100 rows with the script located in the backend folder following thes steps:
#### Steps
 1. Open with your graphic interface (in my case MySQL Workbench) the script located in: BackEnd\src\main\resources\static\ .
 2. Run the entire script.

##### Additional information

 - The web page is dinamic, so if you want to add categories or products
   on the database, it will be automatically updated into the web page.
- There is a .yaml file into the backend folder wich contains sensitive information such as okta and stripe keys. That's why the file is not in GitHub's repository. The app don't without these keys, so if you want to test it out, then you have to create an okta and stripe free account and use your own values. There is also a config.ts file in the frontend foldedr with okta keys, you will need to create a config folder and a file there in order to set the corresponding variables.
##### Own code
- application.yaml (BackEnd side inside resources folder): 

<pre><code>oktaProperties:
  clientId: Your okta client id
  secret: Your okta secret key
  issuer: Your domain
stripeProperties:
  key: Your stripe key</code></pre>
  
 - my-app-config.ts ():
 <pre><code>export default {
 
  oidc: {
    clientId: 'Your okta client id',
    issuer: 'Your domain',
    redirectUri: 'https://localhost:4200/login/callback',
    scopes: ['openid','profile','email']
	}
	
}</code></pre>
