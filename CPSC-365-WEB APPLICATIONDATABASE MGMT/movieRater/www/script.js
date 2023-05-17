$(document).ready(function () {
    $(".addFriend").click(function () {
        var x = $(this).attr('friendID');
        $.post("addFriend.php", {"userID": x}, function (data) {
            console.log(data);
            data = JSON.parse(data);
        });
        $(this).parent().text("Friend Added");
    });
});

$(document).ready(function () {
    $(".addFriendMov").click(function () {
        var x = $(this).attr('friendID');
        $.post("addFriend.php", {"userID": x}, function (data) {
            console.log(data);
            data = JSON.parse(data);
        });
        $(this).parent().text("Request Sent");
    });
});

$(document).ready(function () {
    $(".denyFriend").click(function () {
        var x = $(this).attr('friendID');
        $.post("denyFriend.php", {"userID": x}, function (data){
           console.log(data);
           data = JSON.parse(data);
        });
        $(this).parent().text("Friend Removed");
    });
});