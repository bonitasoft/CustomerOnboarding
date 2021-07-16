function ($scope, $http) {

    function clearPicture() {
        var context = canvas.getContext('2d');
        context.fillStyle = "#AAA";
        context.fillRect(0, 0, canvas.width, canvas.height);
    
        data = canvas.toDataURL('image/png');
        picture.setAttribute('src', data);
    }
  
    function takePicture() {
        var context = canvas.getContext('2d');
        if (width && height) {
          canvas.width = width;
          canvas.height = height;
          context.drawImage(video, 0, 0, width, height);
        
          data = canvas.toDataURL('image/png');
          picture.setAttribute('src', data);
        } else {
          clearPicture();
        }
        
        video.hidden = true;
        picture.hidden = false;
        takePictureButton.setAttribute("disabled", "");
        uploadButton.removeAttribute("disabled");
        newPictureButton.removeAttribute("disabled");
    }
   
    console.log("Start widget initialization");
    
    //var width = 320;    // We will scale the picture width to this
    var width = 0;
    var height = 0; // This will be computed based on the input stream
    
    var streaming = false;
    
    var video = null;
    var canvas = null;
    var picture = null;
    var takePictureButton = null;
    var uploadButton = null;
    var newPictureButton = null
    
    var data = null;

    video = document.getElementById('video');
    canvas = document.getElementById('canvas');
    picture = document.getElementById('picture');
    takePictureButton = document.getElementById('take-picture');
    uploadButton = document.getElementById('upload-picture');
    newPictureButton = document.getElementById('new-picture');
    
    picture.hidden = true;
    uploadButton.setAttribute("disabled", "");
    newPictureButton.setAttribute("disabled", "");
    
    navigator.mediaDevices.getUserMedia({video: true, audio: false})
    .then(function(stream) {
            video.srcObject = stream;
            video.onloadedmetadata = function(e) {
                video.play();
            }
    })
    .catch(function(err) {
            var message = "An error occured! " + err;
            console.log(message);
            notification.innerHTML = message;
    });
    
    video.addEventListener('canplay', function(ev){
        if (!streaming) {
            height = video.videoHeight;
            width = video.videoWidth;
    
            // Firefox currently has a bug where the height can't be read from
            // the video, so we will make assumptions if this happens.    
            if (isNaN(height)) {
                height = width / (4/3);
            }
                
            video.setAttribute('width', width);
            video.setAttribute('height', height);
            canvas.setAttribute('width', width);
            canvas.setAttribute('height', height);
            streaming = true;
        }
    }, false);
    
    takePictureButton.addEventListener('click', function(ev){
        takePicture();
        ev.preventDefault();
    }, false);
    
    newPictureButton.addEventListener('click', function(ev){
      video.hidden = false;
      picture.hidden = true;
      takePictureButton.removeAttribute("disabled");
      uploadButton.setAttribute("disabled", "");
      newPictureButton.setAttribute("disabled", "");
      ev.preventDefault();
    }, false);
    
    uploadButton.addEventListener('click', function(ev){
        var blobBin = atob(data.split(',')[1]);
        var array = [];
        for(var i = 0; i < blobBin.length; i++) {
            array.push(blobBin.charCodeAt(i));
        }
        var file = new Blob([new Uint8Array(array)], {type: 'image/png'});
        
        var fd = new FormData();
        fd.append('file', file);
        
        var request = {
            method: 'POST',
            url: '/bonita/portal/fileUpload',
            headers: {
                'Content-Type': undefined
            },
            transformRequest: angular.identity,
            data: fd
        };
        $http(request).success(function(data, status, headers, config) {
            console.log("success: " + status);
        }).
        error(function(data, status, headers, config) {
            console.log("error: " + status);
        });
        ev.preventDefault();
    }, false);
    
    clearPicture();    
}