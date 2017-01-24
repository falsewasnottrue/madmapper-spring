(function($, global, document) {

    global.state = new Map();

    global.getState = function(fieldName) {
        var s = state.get(fieldName);
        return s || { "type" : "none" };
    }

    global.setDirect = function(fieldName) {
        setState(fieldName, {
            "type": "direct",
            "src": null
        });
    }
    global.setDirectSource = function(fieldName, elem) {
        setState(fieldName, {
            "type": "direct",
            "src": elem.value
        });
    }

    global.setMapping = function(fieldName) {
        setState(fieldName, {
            "type": "mapping",
            "mapping": [ {"id": 1, "key": "", "value": ""} ]
         });
    }

    global.setDefault = function(fieldName) {
        setState(fieldName, {
            "type": "default",
            "value": null
        });
    }
    global.setDefaultValue = function() {
        setState(fieldName, {
            "type": "default",
            "value": elem.value
        });
    }

    global.setNone = function(fieldName) {
        if (confirm("Really set to none?")) {
            setState(fieldName, {"type": "none"});
        }
    }

    global.setState = function(fieldName, nextState) {
        var currState = getState(fieldName)
        $('.select-type[data-field="' + fieldName + '"][data-type="' + currState.type + '"]').hide('slow');
        $('.select-type[data-field="' + fieldName + '"][data-type="' + nextState.type + '"]').show('slow');

        state.set(fieldName, nextState);
    }

    global.sendState = function() {
        console.log(state);
    }
}(window.jQuery, window, document));

$(document).ready(function() {
    $("#accordion").accordion();
    $(".select").checkboxradio({
      icon: false
    });

    $(".select-type").hide();
    $(".select-type").each(function(index, elem) {
        var e = $(elem);
        var s = getState(e.data("field"))
        if (s.type == e.data("type")) { e.show(); }
    });
})
