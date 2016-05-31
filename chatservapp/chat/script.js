var name = 'User';
var appState = {
    mainUrl: 'http://localhost:8080/chat',
    messageList: [],
    messageListForResponse: [],
    isConnected : void 0,
    token: 'TN11EN'
};
function createMessage(name, text, time, deleted, edited) {
        this.id= uniqueId();
        this.name=  name.toString();
        this.timestamp=  time;
        this.text=  text;
        this.deleted= deleted.toString();
        this.edited= edited.toString();
}

function loadName() {
    var inputName = document.getElementById('nch-text');
    inputName.setAttribute('placeholder', name);
}

function uniqueId() {
    return Math.floor(Date.now()* Math.random() * Math.random()).toString();
}
function getTime() {
    return new Date().toLocaleTimeString();
}

function run() {
    var appContainer = document.getElementsByClassName('main-chat-frame')[0];
    appContainer.addEventListener('click', delegateEvent);
    loadName();
    connect();
    /*
    if (!appState.isConnected){
        connect()
    }
    */
}

function connect() {
    if (appState.isConnected) {
        return;
    }

    whileConnected();
}
function whileConnected() {
    appState.isConnected = setTimeout(function () {
            loadMessages(function () {
                render(appState);
                whileConnected()
            });/*);
            render(appState);
            whileConnected();*/
    }, 1000);
}

/*
function connect() {
    appState.isConnected = setTimeout(function () {
            loadMessages();
            render(appState);
            connect();
    }, 500);
}
*/
function delegateEvent(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('nch-button')) {
        onNchButton(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('send-button')) {
        onSendButtonClick(evtObj);
    }
}

function onNchButton() {
    var nameItem = document.getElementById('nch-text');
    if (!nameItem.value)
        return;
    name = nameItem.value;
    nameItem.value='';
    nameItem.setAttribute('placeholder',name);
}

function onSendButtonClick() {
    var mes = textValue();
    if (mes == "" || mes == null)
        return;
    addMessage(new createMessage(name, mes, getTime(), false, false));


}

function onEdit(element) {
    var currentElement = element.parentNode;
    var id = currentElement.attributes['data-message-id'].value;
    var index= appState.messageList.findIndex(function (e) {
        return e.id == id;
    });
    var message = appState.messageList[index];
    if (message.deleted == 'true') {
        return;
    }
    editMessage(currentElement);
}

function editMessage(element) {
    var id = element.attributes['data-message-id'].value;
    var index = appState.messageList.findIndex(function (e) {
        return e.id == id;
    });
    var message = appState.messageList[index];
    var dialog = document.getElementById("mes-change-frame");//
    dialog.style.visibility = 'visible';
    var inputNewMessage = document.getElementById('mch-text-field');
    inputNewMessage.focus();
    document.getElementById('cancel').onclick = function () {
        inputNewMessage.value = "";
        dialog.style.visibility =  "hidden" ;
    };
    document.getElementById('ok').onclick = function () {
        message.text = inputNewMessage.value;
        message.edited = 'true';
        inputNewMessage.value = "";
        dialog.style.visibility = "hidden" ;
        ajax('PUT', appState.mainUrl, JSON.stringify(message));
    }
}

function deleteMessage(id) {
    var url = appState.mainUrl + '?msgId=' + id;
    ajax('DELETE', url, null);
}

function onDelete(element) {
    var currentElement = element.parentNode;
    var id = currentElement.attributes['data-message-id'].value;
    var index = appState.messageList.findIndex(function (e) {
        return e.id == id;
    });
    var message = appState.messageList[index];
    message.deleted = 'true';
    deleteMessage(id);
}

function loadMessages(done ) {
   var url = appState.mainUrl + '?token=' + appState.token;
    ajax('GET', url, null, function (responseText) {
        var response = JSON.parse(responseText);
        appState.messageList = response.messages;

        done();
    });

    /*
    var url = appState.mainUrl + '?token=' + appState.token;
    ajax('GET', url, null, function (responseText) {
        var response = JSON.parse(responseText);
        appState.token = response.token;

        if (response.messages.length > 0) {
            if (appState.messageList.length == 0) {
                appState.messageList = response.messages;
            }
            appState.messageListForResponse = response.messages;
        }

        if (appState.token != response.token) {
            appState.token = response.token;
            render(appState);
        }
        done();
    });
*/
}

function ajax(method, url, data, continueWith) {
    var xhr = new XMLHttpRequest();
    xhr.open(method || 'GET', url, true);
    xhr.onload = function () {
        if (xhr.readyState != 4) {
            return
        }
        if (xhr.status != 200) {
            return;
        }
        if (isError(xhr.responseText)) {
            appState.isConnected = void 0;
            connect();
            return;
        }else{
            document.getElementById('errorIcon').style.visibility = "hidden";
        }
        if (continueWith != null) {
            continueWith(xhr.responseText);
        }
    };

    var errorIcon = document.getElementById('errorIcon');

    xhr.onerror = function (e) {
        errorIcon.style.visibility =  "visible";
        appState.isConnected = void 0;
        connect();
    };
  //  errorIcon.style.visibility = "hidden";

    xhr.send(data);
}

function isError(text) {
    if (text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch (ex) {
        return true;
    }

    return !!obj.error;
}

function addMessage(message) {
    ajax('POST', appState.mainUrl, JSON.stringify(message), function () {
        appState.messageList.push(message);
    });
}

function textValue() {
    var messageTextElement = document.getElementById('message-text-field');
    var messageTextValue = messageTextElement.value;
    messageTextElement.value = '';
    return messageTextValue;
}

function appendToList(list, messageList, messageMap) {
    for (var i = 0; i < messageList.length; i++) {
        var message = messageList[i];
        if (messageMap[message.id] == null) {
            continue;
        }
        messageMap[message.id] = null;
        var child = elementFromTemplate('messageTemplate');
        renderMessageState(child, message);
        list.appendChild(child);
        document.getElementById("idFrame").scrollTop +=1000;
    }
}

function updateList(list, messageMap) {
    var children = list.children;
    var notFound = [];
    for (var i = 0; i < children.length; i++) {
        var child = children[i];
        var id = child.attributes['data-message-id'].value;
        var item = messageMap[id];
        if (item == null) {
            notFound.push(child);
            continue;
        }
        renderMessageState(child, item);
        messageMap[id] = null;
    }
    return notFound;
}

function render(e) {
    var list = document.getElementsByClassName('mes-list')[0];
    var messagesMap = e.messageList.reduce(function (accumulator, message) {
        accumulator[message.id] = message;

        return accumulator;
    }, {});
    updateList(list, messagesMap);
    appendToList(list, e.messageList, messagesMap);
}

function renderMessageState(element, message) {
    element.setAttribute('data-message-id', message.id);

    if(message.name == name) {
        var editIcon = element.getElementsByClassName('editIcon')[0];
        editIcon.style.visibility = "visible";
        var deleteIcon = element.getElementsByClassName('deleteIcon')[0];
        deleteIcon.style.visibility = "visible";
    }

    if (message.deleted == 'true') {
        var deleteIcon = element.getElementsByClassName('deleteIcon')[0];
        deleteIcon.style.visibility = "hidden";
        element.firstChild.textContent = "[" + message.timestamp + "]" + " " + message.name + " ";
        element.lastChild.textContent = "deleted";
        var deletedIcon = element.getElementsByClassName('deletedIcon')[0];
        deletedIcon.style.visibility = "visible";
    }
    else {
        element.firstChild.textContent = "[" + message.timestamp + "]" + " " + message.name + " ";
        element.lastChild.textContent = message.text + " ";

        if (message.edited == 'true') {
            var editedIcon = element.getElementsByClassName('editedIcon')[0];
            editedIcon.style.visibility = "visible";
        }
    }
}

function elementFromTemplate(str) {
    var template = document.getElementById(str);
    return template.firstElementChild.cloneNode(true);
}


