// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
/**
 * Adds a random greeting to the page.
 */
/*function getData() {
    fetch('/data').then(response => response.json()).then((data) => {
    // stats is an object, not a string, so we have to
    // reference its fields to create HTML content
 
    const dataElements = document.getElementById('Comments');
    dataElements.innertext = data;
    dataElements.append(data);
    dataElements.append(
        createListElement(data.get(0)));
   // dataElements.appendChild(
    //    createListElement('Element 2: ' + data.get(1)));
   // dataElements.appendChild(
    //    createListElement('Element 3: ' + data.get(2)));
  });
} */
 
 
function addRandomGreeting() {
  const greetings =
      ['I am 20 years old', 'My first language is French', 'I have a sister', 
      'My favorite band is Pink Floyd', 'I am half Persian'];
 
  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];
 
  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
 
 
function randomizeImage() {
  // The images directory contains 13 images, so generate a random index between
  // 1 and 13.
  const imageIndex = Math.floor(Math.random() * 7) + 1;
  const imgUrl = 'images/IMG_' + imageIndex + '.jpeg';
 
  const imgElement = document.createElement('img');
  imgElement.src = imgUrl; 
 
  var imageContainer = document.getElementById('random-image-container');
  // Remove the previous image.
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
}
 
function showFrame() {
    document.getElementById("showiFrame").innerHTML = "<iframe src=\"https://open.spotify.com/embed/track/1bSpwPhAxZwlR2enJJsv7U\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\" ></iframe>";
}

function getData() {
  fetch('/data').then(response => response.json()).then((data) => {

    const comments = document.getElementById('comments-container');
    comments.innerHTML = '';
    //for (x in data) {
        comments.append(createListElement(data));
    //}
    });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
 
 
/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
 

