(function($, global, document) {

    global.state = new Map();

    global.getState = function(fieldName) {
        var s = state.get(fieldName);
        return s || { "type" : "none" };
    }

    global.setOrigin = function(fieldName, elem) {
        var origin = $(elem).val();
        var s = getState(fieldName)
        s.origin = origin;
    }

    global.setRequired = function(fieldName, elem) {
        var required = $(elem).is(':checked');
        var s = getState(fieldName);
        s.required = required
        state.set(fieldName, s);
    }

    global.setDirect = function(fieldName) {
        setState(fieldName, {
            "type": "direct",
            "src": null
        });
    }
    global.setSource = function(fieldName, elem) {
        var source = elem.value;
        var state = getState(fieldName);
        state.source = source;
    }

    global.setMapping = function(fieldName) {
        setState(fieldName, {
            "type": "mapping",
            "mapping": [ {"id": 1, "key": "KEY", "value": "VALUE"} ]
         });
         showMappingTable(fieldName);
    }

    global.addMapping = function(fieldName) {
        var curr = getState(fieldName)
        curr.mapping.push({"id" : curr.mapping.length + 1, "key": "KEY", "value": "VALUE"});
        showMappingTable(fieldName);
    }

    global.updateMapping = function(fieldName) {
        var data = $('#mapping-table-' + fieldName + ' .dataRow').map(function(index, elem) {
            var x =  $(elem).children('.id');
            var id = $(elem).children('.id').get(0).textContent;
            var key = $(elem).children('.key').get(0).textContent;
            var value = $(elem).children('.value').get(0).textContent;
            return {"id": id, "key": key, "value": value};
        }).toArray();
        state.set(fieldName, {
           "type": "mapping",
           "mapping": data
        });
    }

    global.showMappingTable = function(fieldName) {
        var element = $('#mapping-' + fieldName);
        var data = getState(fieldName).mapping;
        var content = '<table class="mapping-table" id="mapping-table-' + fieldName + '">';
        content += '<tr><th>Id</th><th>Key</th><th>Value</th></tr>';
        data.map(function(d) {
            content += '<tr class="dataRow">' +
                '<td class="id">' + d.id + '</td>' +
                '<td class="key">' + d.key + '</td>'+
                '<td class="value">' + d.value + '</td></tr>';
        });
        content += '</table>';
        element.html(content);

        $('#mapping-table-' + fieldName).Tabledit({
            columns: {
                identifier: [0, 'id'],
                editable: [[1, 'Key'], [2, 'Value']]
            },
            deleteButton: false,
            restoreButton: false,
            onAlways: function() {
                updateMapping(fieldName);
            }
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
