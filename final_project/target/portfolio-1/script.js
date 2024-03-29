function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }
  
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}
function myFunction() { 
    var element = document.body;
    element.classList.toggle("dark-mode");
}


function printComment() {

fetch('/translate').then(response => response.json()).then((comments) => {
    const taskListElement = document.getElementById('translate-comment-container');
    comments.forEach((comment) => {
      taskListElement.appendChild(createCommentElement(comment));
    })
  });
}

/** Creates an element that represents a comment */
function createCommentElement(comment) {
  const taskElement = document.createElement('li');
  taskElement.className = 'comment';
//   taskElement.id = "liStyle";

  const titleElement = document.createElement('span');
  titleElement.innerText = comment.name + ":" +comment.msg;
  taskElement.appendChild(titleElement);

  return taskElement;
}

var slideIndex = 0;
// showSlides();

function showSlides() {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  slideIndex++;
  if (slideIndex > slides.length) {slideIndex = 1}    
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block";  
  dots[slideIndex-1].className += " active";
  setTimeout(showSlides, 3000); // Change image every 2 seconds
}


function fetchBlobstoreUrlAndShowForm() {
    fetch('/blobstore-upload-url').then(response => response.text()).then((quote) => {
     document.getElementById('cause-container').innerHTML = quote;
  });
}

function getCauses(){
    fetch('/translatePage').then(response => response.json()).then(causes => {
    const causeListElement = document.getElementById('post-container-column');
    causeListElement.innerHTML = '';
    console.log(causes);
    for (var i = 0; i < causes.length; i++){
        var obj = causes[i];
        causeListElement.appendChild(createPostUnit(obj));
    }
  });
    // getCauses();
    showSlides();
  printComment();
}


function createPostUnit(obj){
    const divElement = document.createElement('div');
    divElement.className= "column";
    divElement.innerHTML = '';
    divElement.appendChild(addATagToElement(obj.id, obj.imageUrl));
    divElement.appendChild(createBigTextElement(obj.title));
    divElement.appendChild(createParagraphElement(obj.description));
    return divElement;
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

/** Creates an <p> element containing text. */
function createParagraphElement(text) {
  const pElement = document.createElement('p');
  pElement.id = "paragraphBorder";
  pElement.innerText = text;
  return pElement;
}

function createBigTextElement(text) {
  const hElement = document.createElement('h2');
  hElement.innerText = text;
  return hElement;
}

function createImageTag(text){
  var imgElement = document.createElement('img');
  imgElement.id = "img";
  imgElement.src = text;
  return imgElement;
}

function addATagToElement(id, text){
    var aElement = document.createElement('a');
    aElement.innerHTML = "<img id = 'img' src='"+text+"' alt='demo'/>"
    var url  = "/details.html?" + "id=" +id; 
    aElement.href = url   
    return aElement;
}

//trying this
 function detailsOnlode () {
    var url = document.location.href,
        params = url.split('?')[1].split('&'),
        data = {}, tmp;
    for (var i = 0, l = params.length; i < l; i++) {
         tmp = params[i].split('=');
         data[tmp[0]] = tmp[1];
    }
    var id = data.id;

  fetch('/details-data?id=' +id).then(response => response.text()).then((quote) => {
     document.getElementById('container').innerHTML = quote;
  });
}

