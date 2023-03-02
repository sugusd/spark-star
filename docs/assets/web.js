let funcIndex = 1;

function show_func(index) {

    document.getElementById('func-' + funcIndex).style.display = "none";
    document.getElementById('func-' + funcIndex + '-link').style.borderBottom = "none";

    document.getElementById('func-' + index).style.display = "block";
    document.getElementById('func-' + index + '-link').style.borderBottom = "black solid 3px";

    funcIndex = index;
}
