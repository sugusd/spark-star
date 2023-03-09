let funcIndex = 1;

function show_func_link(index) {

    document.getElementById('func-' + funcIndex).style.display = "none";
    document.getElementById('show-func-link-' + funcIndex).style.borderBottom = "none";

    document.getElementById('func-' + index).style.display = "flex";
    document.getElementById('show-func-link-' + index).style.borderBottom = "black solid 3px";

    funcIndex = index;
}

function scrollToDiv(div_name) {
    let div = document.getElementById(div_name);
    div.scrollIntoView({ behavior: 'smooth' });
}

function isMobileDevice() {
    return (typeof window.orientation !== "undefined") || (navigator.userAgent.indexOf('IEMobile') !== -1);
}

function demoBtn() {

    if (isMobileDevice()) {
        alert("请使用电脑访问！");
    } else {
        window.open('https://zhiqingyun-demo.isxcode.com');
    }
}
