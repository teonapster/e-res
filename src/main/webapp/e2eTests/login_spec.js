describe('Authentication Procedure', function() {
  
  var loginURL='http://localhost:8080/eres/app/#/login';
  var email = element(by.name('email1'));
  var password = element(by.name('passwd1'));
  var loginButton = element(by.name('loginBtn'));
  var logoutButton = element(by.name('logoutBtn'));
  var error = element(by.model('loginError'));

  beforeEach(function() {
	browser.get(loginURL);
  });
  
  afterEach(function(){
	
  });
  
  it('should redirect to the login page if trying to load protected page while not authenticated', function() {


    browser.get('http://localhost:8080/eres/app/#/houseList');
    expect(browser.getCurrentUrl()).toEqual(loginURL);
  });

  it('should accept a valid email address and password', function() {
	email.clear();
    password.clear();

    email.sendKeys('admin@admin');
    password.sendKeys('admin');
    loginButton.click();
    expect(browser.getCurrentUrl()).not.toEqual(loginURL);
    logoutButton.click();
  });
});