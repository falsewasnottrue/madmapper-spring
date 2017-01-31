(function($, global, document) {

    global.state = {};

    global.getState = function(fieldName) {
        var s = state[fieldName]
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
        s.required = required;
    }

    global.setDirect = function(fieldName) {
        setState(fieldName, {
            "type": "direct",
            "source": null
        });
    }
    global.setSource = function(fieldName, elem) {
        var source = elem.value;
        var s = getState(fieldName);
        s.source = source;
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
        state[fieldName].mapping = data
    }

    global.showMappingTable = function(fieldName) {
        var element = $('#mapping-' + fieldName);
        var data = getState(fieldName).mapping;
        var content = '<table class="mapping-table table table-hover" id="mapping-table-' + fieldName + '">';
        content += '<tr><th>Id</th><th>Key</th><th>Value</th></tr>';
        data.map(function(d) {
            content += '<tr class="dataRow">' +
                '<td class="id">' + d.id + '</td>' +
                '<td class="key">' + d.key + '</td>'+
                '<td class="value">' + d.value + '</td></tr>';
        });
        content += '</table>';
        element.html(content);

        var table = $('#mapping-table-' + fieldName);
        if (table && table.length > 0) {
            table.Tabledit({
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
    }


    global.setDefault = function(fieldName) {
        setState(fieldName, {
            "type": "default",
            "value": null
        });
    }

    global.setValue = function(fieldName, elem) {
        var value = elem.value;
        var s = getState(fieldName);
        s.value = value;
    }

    global.setNone = function(fieldName) {
        if (confirm("Really set to none?")) {
            setState(fieldName, {"type": "none"});
        }
    }

    global.setState = function(fieldName, nextState) {
        var currState = getState(fieldName);
        $('.select-type[data-field="' + fieldName + '"][data-type="' + currState.type + '"]').hide('slow');
        $('.select-type[data-field="' + fieldName + '"][data-type="' + nextState.type + '"]').show('slow');

        state[fieldName] = nextState;
    }

    global.saveState = function(specName) {
        var jsonData={json:JSON.stringify(state)};
        $.ajax({
          type: "POST",
          url: "/save/" + specName,
          data: jsonData,
          dataType: "json"
          // TODO callback
        });
    }

    global.updateState = function(newState) {
        state = newState;
        console.log(state);
        $(".select-type").hide();
        $(".select-type").each(function(index, elem) {
            var e = $(elem);
            var s = getState(e.data("field"))
            if (s.type == e.data("type")) { e.show(); }
        });

        for (var fieldName in state) {
            if (state.hasOwnProperty(fieldName)) {
                var s = getState(fieldName);
                if (s.required && s.required != "false") { $('.required-' + fieldName).prop('checked', true); }
                if (s.origin) { $('.origin-' + fieldName).val(s.origin); }
                if (s.source) { $('.source-' + fieldName).val(s.source); }
                if (s.value) { $('.value-' + fieldName).val(s.value); }

                if (s.mapping) { showMappingTable(fieldName); }
            }
        }
    }

    global.validate = function(specName) {
        var jsonData={json:JSON.stringify(state)};
        $.ajax({
            type: "POST",
            url: "/validate/" + specName,
            data: jsonData,
            dataType: "json",
            error: function(errorMessage) {
                console.log(errorMessage);
            },
            success: function(validationResult) {
                $('div.error').html('');
                $('h3.error').removeClass('error');

                for (var fieldName in validationResult) {
                    if (validationResult.hasOwnProperty(fieldName)) {
                        $('.title-' + fieldName).addClass('error');
                        $('.error-' + fieldName).html(validationResult[fieldName]);
                    }
                }
            }
        });
    }
}(window.jQuery, window, document));

$(document).ready(function() {
    $("#accordion").accordion({
        collapsible: true
    });
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
