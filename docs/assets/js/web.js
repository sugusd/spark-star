let funcIndex = 1;

function show_func_link(index) {

    document.getElementById('func-' + funcIndex).style.display = "none";
    document.getElementById('show-func-link-' + funcIndex).style.borderBottom = "none";

    document.getElementById('func-' + index).style.display = "block";
    document.getElementById('show-func-link-' + index).style.borderBottom = "black solid 3px";

    funcIndex = index;
}
