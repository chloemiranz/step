// Copyright 2020 Google LLC
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

window.onload = function() {
  getLogin();
  getComments();
  fetchBlobstoreUrlAndShowForm();
};

function addRandomGreeting() {
  const greetings = [
    'I am 20 years old',
    'My first language is French',
    'I have a sister',
    'My favorite band is Pink Floyd',
    'I am half Persian',
  ];

  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


function randomizeImage() {
  const imageIndex = Math.floor(Math.random() * 7) + 1;
  const imgUrl = 'images/IMG_' + imageIndex + '.jpeg';

  const imgElement = document.createElement('img');
  imgElement.src = imgUrl;

  const imageContainer = document.getElementById('random-image-container');

  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
  document.getElementById('dog-box').style.height = 'auto';
}

function showFrame() {
  document.getElementById('showiFrame').innerHTML =
      '<iframe src="https://open.spotify.com/embed/track/1bSpwPhAxZwlR2enJJsv7U" width="100%" height="300px" frameborder="0" allowtransparency="true" allow="encrypted-media" ></iframe>';
  document.getElementById('song-box').style.height = 'auto';
}

function getComments() {
  fetch('/login')
      .then((response) => {
        if (!response.ok) {
          document.getElementById('comment-box').classList.add('hide');
          document.getElementById('comment-box').classList.remove('show');
          throw new Error('Network response was not ok');
        }
        return response.text();
      })
      .then((message) => {
        document.getElementById('comment-box').classList.add('show');
        document.getElementById('comment-box').classList.remove('hide');

        fetch('/data').then((response) => response.json()).then((comments) => {
          const commentsList = document.getElementById('comments-container');
          commentsList.innerHTML = '';
          const maxInput = document.getElementById('max').value;
          const paramInput = 'max='.concat(maxInput);
          const params = new URLSearchParams(paramInput);
          let max = params.get('max');
          if (max == 'all' || max > comments.length) {
            max = comments.length;
          }
          const fetches = [];
          for (let i = 0; i < max; i++) {
            if (comments[i].hasOwnProperty('blobKey') &&
            comments[i].blobKey !== 'noBlob') {
              fetches.push(fetch('/getBlob?blobKey=' + comments[i].blobKey)
                  .then((imgBlob) => {
                    comments[i].url = imgBlob.url;
                  }));
            }
          }
          Promise.all(fetches).then(() => {
            for (let i = 0; i < max; i++) {
              console.log(comments[i]);
              commentsList.append(createCommentElement(comments[i]));
            }
          });
        });
      });
}


function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  const emailCommentDiv = document.createElement('div');
  const emailSpan = document.createElement('span');
  const commentSpan = document.createElement('span');
  const timestampDiv = document.createElement('div');

  emailSpan.classList.add('nickname-text');
  emailCommentDiv.classList.add('comment-text');
  timestampDiv.classList.add('timestamp-text');

  emailSpan.innerText = comment.email + ': ';
  commentSpan.innerText = comment.comment;
  const date = new Date(comment.timestamp);
  timestampDiv.innerText = date.toString();

  commentElement.appendChild(timestampDiv);
  emailCommentDiv.appendChild(emailSpan);
  emailCommentDiv.appendChild(commentSpan);
  commentElement.appendChild(emailCommentDiv);

  if (comment.blobKey !== 'noBlob') {
    const image = document.createElement('IMG');
    image.setAttribute('src', comment.url);
    image.classList.add('comment-image');

    commentElement.appendChild(image);
  }

  return commentElement;
}

function getLogin() {
  fetch('/login').then((response) => response.text()).then((message) => {
    const login = document.getElementById('login-box');
    login.innerHTML = message;
  });
}

function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('my-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
}

function deleteComments() {
  fetch('/delete-data', {method: 'POST'}).then((response) => {
    getComments();
  });
}
