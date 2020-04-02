function checkAll(obj) {
    'use strict';
    let items = obj.form.getElementsByClassName("check"),
        len, i;
    for (i = 0, len = items.length; i < len; i += 1) {
        if (items.item(i).type && items.item(i).type === "checkbox") {
            items.item(i).checked = obj.checked;
        }
    }
}

function checkOne(obj) {
    'use strict';
    let items = obj.form.getElementsByClassName("check"),
        len, i, cnt = 0;
    for (i = 1, len = items.length; i < len; i += 1) {
        if (items.item(i).checked)
            ++cnt;
    }
    items.item(0).checked = cnt === (items.length - 1);
}