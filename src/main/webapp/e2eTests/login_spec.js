describe('Authentication Procedure', function() {
  
  var loginURL='http://localhost:8080/eres/app/#/login';
  var email = element(by.name('email1'));
  var password = element(by.name('passwd1'));
  var loginButton = element(by.name('loginBtn'));
  var error = element(by.model('loginError'));

  
  it('should redirect to the login page if trying to load protected page while not authenticated', function() {
	browser.get(loginURL);

    browser.get('http://localhost:8080/eres/app/#/houseList');
    expect(browser.getCurrentUrl()).toEqual(loginURL);
  });

  it('should accept a valid email address and password', function() {
	browser.get(loginURL);
    email.clear();
    password.clear();

    email.sendKeys('admin@admin');
    password.sendKeys('admin');
    loginButton.click();
    expect(browser.getCurrentUrl()).not.toEqual(loginURL);
  });
});