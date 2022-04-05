const messages = [];
const companies = [];
const guests = [];
while (messages.length > 0) {
    message.pop();
};

$(document).ready(function () {

    // the following code loads messages from JSON file
    $.getJSON('Message.json', function (data) {
        var message_title = '';
        $.each(data, function (i, item) {

            const message = {};
            message.title = item.title;
            message.description = item.description;
            messages.push(message);

            message_title += "<option value='" + item.title + "'>" + item.title + "</option>"
            $("#message").html(message_title)

        })
    })

    // the following code loads company information from JSON file
    $.getJSON('Companies.json', function (data) {
        var company_name = '';
        $.each(data, function (i, item) {

            const company = {};
            company.id = item.id;
            company.company = item.company;
            company.city = item.city;
            company.timezone = item.timezone;
            companies.push(company);

            company_name += "<option value ='" + item.id + "'>" + item.company + "</option>"
            $("#company").html(company_name)

        })
    })

    // the following code loads guest information from JSON file
    $.getJSON('Guests.json', function (data) {
        var guest_name = '';
        $.each(data, function (i, item) {

            const guest = {};
            guest.id = item.id;
            guest.firstName = item.firstName;
            guest.lastName = item.lastName;

            const reservation = {};
            reservation.roomNumber = item.reservation.roomNumber;
            guest.reservation = reservation;
            guests.push(guest);

            guest_name += "<option value ='" + item.id + "'>" + item.firstName + ' ' + item.lastName + "</option>"
            $("#guest").html(guest_name);

        })
    })
});

// function for user to create their own message
function createMessage() {
    var message_title = '';
    var title = $("#title").val();
    var description = $("#description").val();

    const newMessage = {};
    newMessage.title = title;
    newMessage.description = description;
    messages.push(newMessage);

    for (var i = 0; i < messages.length; i++) {
        message_title += "<option value='" + messages[i].title + "'>" + messages[i].title + "</option>";
        $("#message").html(message_title);
    }
};

// function to generate final message
function generateMessage() {
    var msgSelected = $("#message").val();
    var guestSelected = parseInt($("#guest").val());
    var companySelected = parseInt($("#company").val());

    const selectedMessage = messages.find(({ title }) => title === msgSelected);
    const selectedGuest = guests.find(({ id }) => id === guestSelected);
    const selectedCompany = companies.find(({ id }) => id === companySelected);

    // the following code finds the time of day for final message
    var today = new Date();
    var time = today.getHours();
    var timeOfDay;
    if (time < 12) {
        timeOfDay = "Good Morning";
    } else if (time >= 12 && time < 17) {
        timeOfDay = "Good Afternoon";
    } else {
        timeOfDay = "Good Evening";
    }

    // The following code is to replace placeholders within message but does not function properly yet
    //var newMessage = selectedMessage.description.replace("|timeOfDay|", timeOfDay);

    //var placeholders = newMessage.match(/\|(.*?)\|/g);
    //placeholders.forEach(function (placeholder) {
    //    //Placeholder - |firstName|
    //    var phText = placeholder.substring(1, placeholder.length - 1);
    //    //phText = Name                

    //    if (newMessage.includes(placeholder)) {
    //        newMessage = newMessage.replace(placeholder, selectedGuest[phText]).replace(placeholder, selectedCompany[phText]);
    //    }

    //})

    var newMessage = selectedMessage.description.replace("|timeOfDay|", timeOfDay).replace("|firstName|", selectedGuest.firstName).replace
        ("|company|", selectedCompany.company).replace("|roomNumber|", selectedGuest.reservation.roomNumber);


    $("#outputMessage").text(newMessage);
}