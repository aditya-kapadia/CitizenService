  (function(){
  // Initialize Firebase
  //document.writeln("<script type='text/javascript' src='firebase.js'></script>");
  /*var config = {
    apiKey: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
    authDomain: "firebro-cdad2.firebaseapp.com",
    databaseURL: "https://firebro-cdad2.firebaseio.com",
    projectId: "firebro-cdad2",
    storageBucket: "",
    messagingSenderId: "341106678697"
  };
  firebase.initializeApp(config);*/
  const txtEmail = document.getElementById('txtEmail');
  const txtPassword = document.getElementById('txtPassword');
  const btnLogin = document.getElementById('btnLogin');
  const btnSignUp = document.getElementById('btnSignUp');
  /*const btnLogout = document.getElementById('btnLogout');*/

  btnLogin.addEventListener('click', e=>{
  const email = txtEmail.value;
  const pass = txtPassword.value;
  const auth = firebase.auth();
  //Sign In
  const promise = auth.signInWithEmailAndPassword(email, pass);
  promise
  .catch(function (error){
    var errorCode = error.code;
    var errorMessage = error.message;
    if(errorCode == 'auth/wrong-password'){
      alert("Please Enter Correct Password");
    }
    else if(errorCode=='auth/invalid-email'){
      alert("Please Enter Correct Email-Id");
    }
    else if(errorCode=='auth/user-not-found'){
      alert("User Does Not exist!");
    }
      else{
        alert(errorCode);
      }
  console.log(error);
});
  /*(e => console.log(e.message));*/
});

//Add signup Event
btnSignUp.addEventListener('click', e=>{
  const email = txtEmail.value;
  const pass = txtPassword.value;
  if(pass=="" || email=="")
  {
    alert("Please enter Email and Password and press Sign-Up");
  }
  else{
  const auth = firebase.auth();
  //Sign In
  const promise = auth.createUserWithEmailAndPassword(email, pass);
  promise
  .catch(function (error){
    var errorCode = error.code;
    var errorMessage = error.message;
    if(errorCode=='auth/weak-password'){
      alert('Password should be atleast 6 characters long');
    }
    else if(errorCode == 'auth/email-already-in-use'){
      alert("User already created");
    }
      else{
        alert(errorCode);
      }
  console.log(error);
  });/*.catch(e => console.log(e.message));*/
}
});

/*btnLogout.addEventListener('click', e=>{
  firebase.auth().signOut();
  alert("Logout Success");
});*/

firebase.auth().onAuthStateChanged(firebaseUser => {
  if(firebaseUser){
    window.location='home.php';
    //console.log(firebaseUser);
    /*btnLogout.classList.remove('hide');*/
  } else {
    //alert('Please Sign-in with proper credentials');
    console.log('Not Logged In');
    /*btnLogout.classList.add('hide');*/
  }
});

}());