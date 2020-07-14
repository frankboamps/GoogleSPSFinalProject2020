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
   createMap();
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


let map;

/* Editable marker that displays when a user clicks in the map. */
let editMarker;

/** Creates a map that allows users to add markers. */
function createMap() {
  map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 40.730610, lng: -73.935242}, zoom: 12});

  // When the user clicks in the map, show a marker with a text box the user can
  // edit.
  map.addListener('click', (event) => {
    createMarkerForEdit(event.latLng.lat(), event.latLng.lng());
  });

  fetchMarkers();
}

/** Fetches markers from the backend and adds them to the map. */
function fetchMarkers() {
  fetch('/markers').then(response => response.json()).then((markers) => {
    markers.forEach(
        (marker) => {
            createMarkerForDisplay(marker.lat, marker.lng, marker.content)});
  });
}

/** Creates a marker that shows a read-only info window when clicked. */
function createMarkerForDisplay(lat, lng, content) {
  const marker =
      new google.maps.Marker({position: {lat: lat, lng: lng}, map: map});

  const infoWindow = new google.maps.InfoWindow({content: content});
  marker.addListener('click', () => {
    infoWindow.open(map, marker);
  });
}

/** Sends a marker to the backend for saving. */
function postMarker(lat, lng, content) {
  const params = new URLSearchParams();
  params.append('lat', lat);
  params.append('lng', lng);
  params.append('content', content);

  fetch('/markers', {method: 'POST', body: params});
}

/** Creates a marker that shows a textbox the user can edit. */
function createMarkerForEdit(lat, lng) {
  // If we're already showing an editable marker, then remove it.
  if (editMarker) {
    editMarker.setMap(null);
  }

  editMarker =
      new google.maps.Marker({position: {lat: lat, lng: lng}, map: map});

  const infoWindow =
      new google.maps.InfoWindow({content: buildInfoWindowInput(lat, lng)});

  // When the user closes the editable info window, remove the marker.
  google.maps.event.addListener(infoWindow, 'closeclick', () => {
    editMarker.setMap(null);
  });

  infoWindow.open(map, editMarker);
}

/**
 * Builds and returns HTML elements that show an editable textbox and a submit
 * button.
 */
function buildInfoWindowInput(lat, lng) {
  const textBox = document.createElement('textarea');
  const button = document.createElement('button');
  button.appendChild(document.createTextNode('Submit'));

  button.onclick = () => {
    postMarker(lat, lng, textBox.value);
    createMarkerForDisplay(lat, lng, textBox.value);
    editMarker.setMap(null);
  };

  const containerDiv = document.createElement('div');
  containerDiv.appendChild(textBox);
  containerDiv.appendChild(document.createElement('br'));
  containerDiv.appendChild(button);

  return containerDiv;
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
    divElement.innerHTML = '';
    divElement.appendChild(createImageTag(obj.imageUrl));
    divElement.appendChild(createBigTextElement(obj.title));
    divElement.appendChild(createParagraphElement(obj.description));
    return divElement;
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

/** Creates an <p> element containing text. */
function createParagraphElement(text) {
  const pElement = document.createElement('p');
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
  imgElement.id = "post-img";
  imgElement.src = text;
  return imgElement;
}


// //React

// import * as React from 'react';   

// // export default class Entry extends React.Component {
// // constructor(props) {
// //     super(props);
// //   this.buttonHandler = new ButtonHandler();
// // }

// // render() {
// //     return (
// //         <div>
// //             <title>Button example</title>
// //             <button onclick={this.buttonHandler.writeToConsole}>Button</button>
// //         </div>

// //     )
// //   }
// // }

// class TypesOfFood extends React.Component {
//   constructor(props) {
//     super(props);
//   }
//   render() {
//     return (
//       <div>
//         <h1>Types of Food:</h1>
//         {/* change code below this line */}
//         <Fruits />
//         <Vegetables />
//         {/* change code above this line */}
//       </div>
//     );
//   }
// };

// // change code below this line
// ReactDOM.render(<TypesOfFood />, document.getElementById('challenge-node'))