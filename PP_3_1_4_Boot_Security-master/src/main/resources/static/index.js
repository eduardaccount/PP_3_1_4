// ПОЛУЧЕНИЕ АВТОРИЗОВАННОГО ЮЗЕРА
function getUser() {
    fetch("/rest/getCurrentUser",
        {method: 'GET', dataType: 'json'})
        .then(response => response.json())
        .then(user => {
            let temp = "";
            temp += `<tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.userAge}</td>
            <td>${user.userEmail}</td>
            <td>${user.roles.map(e => e.name).join(", ")}</td>
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;
        });
}
getUser();

// ПОЛУЧЕНИЕ СПИСКА ЮЗЕРОВ
function getAllUsers() {
    let temp = '';
    fetch("/rest/getAllUsers",
        {method: 'GET', dataType: 'json'})
        .then(res => res.json())
        .then(users => {
            users.forEach(function (user) {
                temp += `
                <tr>
                <td>${user.userId}</td>
                <td>${user.userName}</td>
                <td>${user.userAge}</td>
                <td>${user.userEmail}</td>
                <td>${user.roles.map(e => " " + e.name)}</td>
                <td><button type="submit" onclick="modalWindowEditUser(${user.userId})"
                class="btn btn-info" data-toggle="modal" data-target="#updateUser">Edit</button></td>
                <td><button type="submit" onclick="modalWindowDeleteUser(${user.userId})" 
                class="btn btn-danger" data-toggle="modal" data-target="#deleteUser">Delete</button></td>
              </tr>`;
            });
            document.getElementById("users").innerHTML = temp;
        });
}
getAllUsers();

// ВЫЗОВ ОКНА ИЗМЕНЕНИЯ ЮЗЕРА
function modalWindowEditUser(id) {
    fetch("/rest/getUser/" + id,
        {method: 'GET', dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#updateId').val(user.userId)
                $('#updateUsername').val(user.userName)
                $('#updateAge').val(user.userAge)
                $('#updateEmail').val(user.userEmail)
                $('#updatePassword').val(user.userPassword)
                $('#updateRoles').val(user.roles.map(e => " " + e.name))
            });
        });
}

// ИЗМЕНЕНИЕ ЮЗЕРА
async function updateUser() {
    let user = {
        userId: document.getElementById('updateId').value,
        userName: document.getElementById('updateUsername').value,
        userAge: document.getElementById('updateAge').value,
        userEmail: document.getElementById('updateEmail').value,
        userPassword: document.getElementById('updatePassword').value,
    }

    await fetch("/rest/updateUser",
        {
            method: 'POST',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify(user)
        })

    $("#updateUser .close").click();
    getAllUsers();
    getUser();
}

// ВЫЗОВ ОКНА ДЛЯ УДАЛЕНИЯ ЮЗЕРА
function modalWindowDeleteUser(id) {
    fetch("/rest/getUser/" + id,
        {method: 'GET', dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.userId)
                $('#deleteUsername').val(user.userName)
                $('#deleteAge').val(user.userAge)
                $('#deleteEmail').val(user.userEmail)
                $('#deleteRoles').val(user.roles.map(e => " " + e.name))
            })
        })
}

// УДАЛЕНИЕ ЮЗЕРА
async function deleteUser() {
    await fetch("/rest/deleteUser/" + document.getElementById("deleteId").value,
        {method: 'GET', dataType: 'json'})
    $("#deleteUser .close").click();

    getAllUsers();
    getUser();
}

// ДОБАВЛЕНИЕ ЮЗЕРА
async function addNewUser() {
    let user = {
        userName: document.getElementById('newUsername').value,
        userAge: document.getElementById('newAge').value,
        userEmail: document.getElementById('newEmail').value,
        userPassword: document.getElementById('newPassword').value,
        roles: $('#newRoles').val(),
    }
    await fetch("/rest/saveUser",
        {
            method: 'POST',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify(user)
        })
    getAllUsers();
    getUser();
}