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
 
function addRandomGreeting() {
  const greetings =
      ['I am 20 years old', 'My first language is French', 'I have a sister', 
      'My favorite band is Pink Floyd', 'I am half Persian'];
 
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];
 
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
 
 
function randomizeImage() {
  const imageIndex = Math.floor(Math.random() * 7) + 1;
  const imgUrl = 'images/IMG_' + imageIndex + '.jpeg';
 
  const imgElement = document.createElement('img');
  imgElement.src = imgUrl; 
 
  var imageContainer = document.getElementById('random-image-container');

  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
}
 
function showFrame() {
    document.getElementById("showiFrame").innerHTML = "<iframe src=\"https://open.spotify.com/embed/track/1bSpwPhAxZwlR2enJJsv7U\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\" ></iframe>";
}

function getComments() {
  fetch('/data').then(response => response.json()).then((comments) => {

    const commentsList = document.getElementById('comments-container');
    commentsList.innerHTML = '';
    for (x in comments) {
        commentsList.append(createListElement(comments[x]));
    }
    });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
 
 function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
 

