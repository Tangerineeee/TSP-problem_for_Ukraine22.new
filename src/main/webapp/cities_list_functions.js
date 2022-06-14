let citiesArray = [];
let indexOfLi = 0
function addCity() {
    indexOfLi++
    const idToSet = indexOfLi
    const f = document.getElementById("city");
    const chosenCity = f.options[f.selectedIndex].value;
    if (citiesArray.length && citiesArray.filter(city => city.value === chosenCity).length !== 0) {
        alert('Це місто вже є у вашому списку, додайте, будь ласка, інше')
        return
    }

    citiesArray.push({id: idToSet, value: chosenCity})

    const cityToAdd = document.createElement("li")
    cityToAdd.className = "list-item"
    const itemText = document.createElement("p")
    itemText.textContent += chosenCity
    cityToAdd.appendChild(itemText)

    cityToAdd.id = indexOfLi.toString()
    const removeButton = document.createElement("button")
    removeButton.className = "delete-button"

    removeButton.onclick = function () {
        removeCity(idToSet)
    }

    document.getElementById('chosenCitiesList').appendChild(cityToAdd).appendChild(removeButton)
    document.getElementById('chosenCities').className = 'show';
    document.getElementById('emptyList').className = 'hide';
    let citiesString = '';
    citiesArray.map(obj => citiesString += obj.value + ' ')
    document.getElementById('citiesList').setAttribute('value', citiesString);
    // console.log(citiesString)
}
const removeCity = (id) => {
    document.getElementById(id).remove();
    citiesArray = citiesArray.filter(el => el.id !== id)
}

function validateNumberOfCities() {
    if (citiesArray.length < 2) {
        alert("Будь ласка, оберіть мінімум 2 міста для побудови маршруту")
        return false
    }
    return true
}