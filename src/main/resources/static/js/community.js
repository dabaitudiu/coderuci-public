function localRefresh() {
    // $('#table_refresh').load("/");
    // window.location.replace(window.location.href + "?order=time");
    $("#comment_list").load(window.location.href + "/local?order=time");
}

function localRefreshByLike() {
    // $('#table_refresh').load("/");
    // window.location.replace(window.location.href + "?order=like");
    $("#comment_list").load(window.location.href + "/local?order=like");
}

/**
 * expand / collapse second-level comments
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var collapse = e.getAttribute("collapsed");

    if (collapse) {
        comments.addClass("show");
        e.removeAttribute("collapsed");
        e.classList.remove("active");
    } else {
        comments.removeClass("show");
        e.setAttribute("collapsed", "");
        e.classList.add("active");
    }
}

function like(item) {
    var likebtn = $("#"+item.id);
    console.log(likebtn);
    var likeCount = $("#count"+item.id);
    var btnData = item.getAttribute("data-btn");
    if (!likebtn.hasClass("active")) {
        likebtn.addClass("active");
        likeCount.html(parseInt(btnData+1));

        // $.ajax({
        //     type: "GET",
        //     url: "/comment/like",
        //     data: JSON.stringify({
        //         "parentId": targetId,
        //         "content": content,
        //         "type": type
        //     }),
        //     success: function (response) {
        //         if (response.code == 200) {
        //             window.location.reload();
        //         } else {
        //             if (response.code == 2005) {
        //                 var isAccepted = confirm(response.message);
        //                 if (isAccepted) {
        //                     window.open("https://github.com/login/oauth/authorize?client_id=594bafe71575c61e9eec&redirect_uri=http://localhost:8666/callback&scope=user&state=1");
        //                     window.localStorage.setItem("closable", true);
        //                 }
        //             } else {
        //                 alert(response.message);
        //             }
        //         }
        //         console.log(response);
        //     },
        //     dataType: "json"
        // });
    }
}

function shine(sec) {
    var id = sec.id;
    // console.log(id);
    var button = $("#"+id);
    if(button.hasClass("btn-light")) {
        button.removeClass("btn-light");
        button.addClass("btn-info");

        var subCommentContainer = $("#card-" + id);

        if (!button.hasClass("block-add-more")) {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    console.log(data.data);
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "mr-3 avatar-list rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h7/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "style" : "margin-top : 7px;",
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu",
                        "style": "margin-top: 10px;"
                    }).append($("<span/>", {
                        "style": "font-size: 0.8rem; color:#999;",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media",
                        "style": "height: 75%;width:auto;"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            });
            button.addClass("block-add-more");
        }

    } else {
        button.addClass("btn-light");
        button.removeClass("btn-info");
    }
}

function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    // console.log("trying to post on " + questionId + " with content: " + content);
    commentToTarget(questionId, 1, content);
}

function commentToTarget(targetId, type, content) {
    var pattern = /mytest|acrotomophilia|alabama hot pocket|alaskan pipeline|anal|anilingus|anus|apeshit|arsehole|ass|asshole|assmunch|auto erotic|autoerotic|babeland|baby batter|baby juice|ball gag|ball gravy|ball kicking|ball licking|ball sack|ball sucking|bangbros|bareback|barely legal|barenaked|bastard|bastardo|bastinado|bbw|bdsm|beaner|beaners|beaver cleaver|beaver lips|bestiality|big black|big breasts|big knockers|big tits|bimbos|birdlock|bitch|bitches|black cock|blonde action|blonde on blonde action|blowjob|blow job|blow your load|blue waffle|blumpkin|bollocks|bondage|boner|boob|boobs|booty call|brown showers|brunette action|bukkake|bulldyke|bullet vibe|bullshit|bung hole|bunghole|busty|butt|buttcheeks|butthole|camel toe|camgirl|camslut|camwhore|carpet muncher|carpetmuncher|chocolate rosebuds|circlejerk|cleveland steamer|clit|clitoris|clover clamps|clusterfuck|cock|cocks|coprolagnia|coprophilia|cornhole|coon|coons|creampie|cum|cumming|cunnilingus|cunt|darkie|date rape|daterape|deep throat|deepthroat|dendrophilia|dick|dildo|dingleberry|dingleberries|dirty pillows|dirty sanchez|doggie style|doggiestyle|doggy style|doggystyle|dog style|dolcett|domination|dominatrix|dommes|donkey punch|double dong|double penetration|dp action|dry hump|dvda|eat my ass|ecchi|ejaculation|erotic|erotism|escort|eunuch|fag|faggot|fecal|felch|fellatio|feltch|female squirting|femdom|figging|fingerbang|fingering|fisting|foot fetish|footjob|frotting|fuck|fuck buttons|fuckin|fucking|fucktards|fudge packer|fudgepacker|futanari|gang bang|gay sex|genitals|giant cock|girl on|girl on top|girls gone wild|goatcx|goatse|god damn|gokkun|golden shower|goodpoop|goo girl|goregasm|grope|group sex|g-spot|guro|hand job|handjob|hard core|hardcore|hentai|homoerotic|honkey|hooker|hot carl|hot chick|how to kill|how to murder|huge fat|humping|incest|intercourse|jack off|jail bait|jailbait|jelly donut|jerk off|jigaboo|jiggaboo|jiggerboo|jizz|juggs|kike|kinbaku|kinkster|kinky|knobbing|leather restraint|leather straight jacket|lemon party|lolita|lovemaking|make me come|male squirting|masturbate|menage a trois|milf|missionary position|motherfucker|mound of venus|mr hands|muff diver|muffdiving|nambla|nawashi|negro|neonazi|nigga|nigger|nig nog|nimphomania|nipple|nipples|nsfw images|nude|nudity|nympho|nymphomania|octopussy|omorashi|one cup two girls|one guy one jar|orgasm|orgy|paedophile|paki|panties|panty|pedobear|pedophile|pegging|penis|phone sex|piece of shit|pissing|piss pig|pisspig|playboy|pleasure chest|pole smoker|ponyplay|poof|poon|poontang|punany|poop chute|poopchute|porn|porno|pornography|prince albert piercing|pthc|pubes|pussy|queaf|queef|quim|raghead|raging boner|rape|raping|rapist|rectum|reverse cowgirl|rimjob|rimming|rosy palm|rosy palm and her 5 sisters|rusty trombone|sadism|santorum|scat|schlong|scissoring|semen|sex|sexo|sexy|shaved beaver|shaved pussy|shemale|shibari|shit|shitblimp|shitty|shota|shrimping|skeet|slanteye|slut|s&m|smut|snatch|snowballing|sodomize|sodomy|spic|splooge|splooge moose|spooge|spread legs|spunk|strap on|strapon|strappado|strip club|style doggy|suck|sucks|suicide girls|sultry women|swastika|swinger|tainted love|taste my|tea bagging|threesome|throating|tied up|tight white|tit|tits|titties|titty|tongue in a|topless|tosser|towelhead|tranny|tribadism|tub girl|tubgirl|tushy|twat|twink|twinkie|two girls one cup|undressing|upskirt|urethra play|urophilia|vagina|venus mound|vibrator|violet wand|vorarephilia|voyeur|vulva|wank|wetback|wet dream|white power|wrapping men|wrinkled starfish|xx|xxx|yaoi|yellow showers|yiffy|zoophilia|🖕/ig;
    if (content.length < 15 || content.length > 5000) {
        alert("Input Length should be 15 to 500 characters");
    } else {
        if (content.match(pattern)) {
            alert("Please watch your language");
            content = content.replace(pattern, "****" );
            document.getElementById('comment_content').value = content;
        } else {
            $.ajax({
                type: "POST",
                url: "/comment",
                contentType: 'application/json',
                data: JSON.stringify({
                    "parentId": targetId,
                    "content": content,
                    "type": type
                }),
                success: function (response) {
                    if (response.code == 200) {
                        window.location.reload();
                    } else {
                        if (response.code == 2005) {
                            var isAccepted = confirm(response.message);
                            if (isAccepted) {
                                window.open("https://github.com/login/oauth/authorize?client_id=594bafe71575c61e9eec&redirect_uri=http://localhost:8666/callback&scope=user&state=1");
                                window.localStorage.setItem("closable", true);
                            }
                        } else {
                            alert(response.message);
                        }
                    }
                    console.log(response);
                },
                dataType: "json"
            });
        }
    }
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    // console.log("id: " + commentId);
    var content = $("#second_comment-" + commentId).val();
    // console.log("content: " + content);
    commentToTarget(commentId, 2, content)
}

function selectTag(component) {
    var value = component.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (!previous) {
        $("#tag").val(value);
    } else {
        if (!previous.includes(value)) {
            $("#tag").val(previous + ';' + value);
        }
    }
}

function showTagList() {
    $("#taglist").show();
}

$(document).mouseup(function(e){
    var _con = $('#tag');
    var _con2 = $('#taglist');
    if((!_con.is(e.target) && _con.has(e.target).length === 0) &&
        (!_con2.is(e.target) && _con2.has(e.target).length === 0)){
        $("#taglist").css("display","none");
    }
});
