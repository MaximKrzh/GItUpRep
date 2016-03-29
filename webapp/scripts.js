/*
 *черновая версия .не совсем понятны отдельные пункты
 */
var isError = 1;
var userName = 'User';

var msList = [];

var theMessage = function(msgText, userName) {
    return {
        text: msgText,
        name: userName,
        id: uniqueId(),
        isDeleted: false,
        isEdited: false
    };
};


function output(value) {
    var output = document.getElementById('output');

    output.innerText = "var taskList = " + JSON.stringify(value, null, 2) + ";";
}

function restore() {
    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("TODOs taskList");

    return item && JSON.parse(item);
}

function store(listToSave) {
    output(listToSave);

    if(typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    localStorage.setItem("TODOs taskList", JSON.stringify(listToSave));
}

function restore() {
    if (typeof(Storage) == "undefined") {
        alert('localStorage is not accessible');
        return;
    }

    var item = localStorage.getItem("TODOs taskList");

    return item && JSON.parse(item);
}






var uniqueId = function () {
    var date = Date.now();
    var random = Math.random() * Math.random();
    return Math.floor(date * random).toString();
};

function run() {
    var appContainer = document.getElementsByClassName('chat')[0];
    appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {

    errorChecker(1);

    if (evtObj.type === 'click') {
        if (evtObj.target.classList.contains('msg-send-button')) {
            onAddButtonClick(evtObj);
        } else if (evtObj.target.id === 'deleteId' /*classList.contains('delete')*/) {
            onDeleteButtonClick(evtObj);
        } else if (evtObj.target.classList.contains('msgChecked')) {
            onCancelButtonClick(evtObj);
            //onDelButtonClick(evtObj);
            //onAddButtonClick(evtObj);
        } else if (evtObj.target.classList.contains('msg')) {
            onCheckedButtonClick(evtObj);
            //onDelButtonClick(evtObj);
            //onAddButtonClick(evtObj);
        } else if (evtObj.target.id === 'editId') {
            onEditButtonClick(evtObj);
        } else if (evtObj.target.id === 'nchBtnId') {
            onEditAuthor(evtObj);

        }
        /*else	if(evtObj.type === 'click' && evtObj.target.classList.contains('msgChecked') ){
         cancel(evtObj);
         //onAddButtonClick(evtObj);
         }*/
    }
}


function errorChecker() {

    if (isError === 1) {
        document.getElementById("errorId").style.visibility = 'visible';
        //var n = document.getElementById('errorId')
    } else {
        document.getElementById("errorId").style.visibility = 'hidden';
    }

}

function onEditAuthor(e) {
    var aList = document.getElementsByClassName('msgAuthor');
    var tn = userName;
    userName = document.getElementById('nchTextId').value;
   //document.getElementById('nchTextId').setAttribute( 'placeholder','enter nickname');
    if (userName !== '') {
        for (var i = 0; i < aList.length; i++) {
            if (aList[i].getAttribute('data-Author') === 'me') {
                aList[i].innerHTML = userName;
                document.getElementById('nchTextId').setAttribute( 'placeholder',userName);
                document.getElementById('nchTextId').value='';
            }
        }
    }else{
        userName=tn;
        alert("empty field")
    }
}

function onCancelButtonClick(ee) {
    var tempId = ee.target.id;
    var txt1 = document.getElementById(ee.target.id);
//	alert("222222222222");
    txt1.className = 'msg';
//	txt1.setAttribute('id',tempId);

}

function onCheckedButtonClick(e) {

    var txt = document.getElementById(e.target.id);
    txt.className = 'msgChecked';

}

function onEditButtonClick(e) {
    var dmList = document.getElementsByClassName('msgChecked');
    var todoText = document.getElementById('msgInputId');

    if (todoText.value !== '') {
        for (var i = 0; i < dmList.length; i++) {
            if (dmList[i].getAttribute('data-isDeleted') !== 'true') {

                var n = dmList[i].getElementsByClassName('msgText');
                n[0].innerHTML = todoText.value;

                //			dmList[i].innerHTML = todoText.value;
                //			dmList[i].className = 'msgEdited';

                dmList[i].setAttribute('data-isEdited', 'true');
                dmList[i].className = 'msg';
            }
        }
    }
    todoText.value = '';
}

function onDeleteButtonClick(e) {

    var dmList = document.getElementsByClassName('msgChecked');

    for (var i = 0; i < dmList.length; i++) {
        //dmList[i].innerHTML='deleted' ;
        dmList[i].setAttribute('data-isDeleted', 'true');
        var n = dmList[i].getElementsByClassName('msgText');
        n[0].innerHTML = 'deleted';
        dmList[i].className = 'msg';

    }

//	alert(txt.className);
//	alert(e.target.id); // удаление в две функции отметить кликом удалить в другой

}

function onAddButtonClick() {
    var todoText = document.getElementById('msgInputId');
    addTodo(todoText.value);
    todoText.value = '';

}

function addTodo(value) {
    if (!value) {
        return;
    }
    var msg = createItem(value);
    var msgList = document.getElementsByClassName('msgsList')[0];
    msgList.appendChild(msg);

}

function createItem(text) {
    var divItem = document.createElement('li');
//	var checkbox = document.createElement('input');
//	checkbox.classList.add('item-check');

    var athr = document.createElement('span');
    athr.classList.add('msgAuthor');
    athr.setAttribute('data-Author', 'me');

    athr.appendChild(document.createTextNode(userName));

    var br = document.createElement('br');

    var msgText = document.createElement('span');
    msgText.classList.add('msgText');
    msgText.appendChild(document.createTextNode(text));


    divItem.setAttribute('id', uniqueId());
    divItem.setAttribute('data-isDeleted', 'false');
    divItem.setAttribute('data-isEdited', 'false');
    divItem.classList.add('msg');


//	checkbox.setAttribute('type', 'checkbox');
//	checkbox.checked=false;

//	divItem.appendChild(checkbox);
    divItem.appendChild(athr);
    divItem.appendChild(br);
    divItem.appendChild(msgText);
    //divItem.appendChild(document.createTextNode(text));

    return divItem;
}
