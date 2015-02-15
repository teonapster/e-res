describe('Authentication Procedure', function() {
  
  var loginURL='http://localhost:8080/eres/app/#/login';
  var houselistURL = 'http://localhost:8080/eres/app/#/houseList';
  var email = element(by.name('email1'));
  var password = element(by.name('passwd1'));
  var loginButton = element(by.name('loginBtn'));
  var logoutButton = element(by.name('logoutBtn'));
  var error = element(by.model('loginError'));
  var firstHouseLink;
	var userStatus =[{
		user:"admin@admin",
		uid:2,
		lastLogin:"2015-01-01T19:39:45.033",
		siteAdmin:true,
		orgid:-1,
		orgAdmin:false
	}]
  
  
	beforeEach(function() {
		browser.get(loginURL);
	});
	
	afterEach(function(){
		//no implementation yet
	})

	it('should accept a valid email address and password', function() {
	email.clear();
    password.clear();

    email.sendKeys('admin@admin');
    password.sendKeys('admin');
    loginButton.click();
    expect(browser.getCurrentUrl()).not.toEqual(loginURL);
  });
	
  it('should show houselist', function() {
	browser.get(houselistURL);
    expect(browser.getCurrentUrl()).toEqual(houselistURL);
//    logoutButton.click();
  });
  
  it('should visit the house with id equal to 2',function(){
	  browser.get(houselistURL);
	firstHouseLink = element(by.name('viewHouse2'));
	firstHouseLink.click();
	expect(browser.getCurrentUrl()).toEqual("http://localhost:8080/eres/app/#/house/2");
	logoutButton.click();
  });

});