
 

function renderStatus(statusText) {
  document.getElementById('url').value=statusText;
} 


function getCurrentTabUrl(callback) {
  var queryInfo = {
    active: true,
    currentWindow: true
  };

  chrome.tabs.query(queryInfo, function(tabs) {
    var tab = tabs[0];
    var url = tab.url;
    console.assert(typeof url == 'string', 'tab.url should be a string');

    callback(url);
  });
}



function extractDomain(url) {
    var domain;
    //find & remove protocol (http, ftp, etc.) and get domain
    if (url.indexOf("://") > -1) {
        domain = url.split('/')[2];
    }
    else {
        domain = url.split('/')[0];
    }

    //find & remove port number
    domain = domain.split(':')[0];

    return domain;
}


document.addEventListener('DOMContentLoaded', function() {
	getCurrentTabUrl(function(url) {
	// Put the image URL in Google search.
		renderStatus('Performing Google Image search for ' + url);
    	renderStatus(extractDomain(url));
	});
});