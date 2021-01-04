function copyToClipboard() {
    let copyText = document.getElementById("copy-email");
    copyText.select();
    document.execCommand("Copy");
  }