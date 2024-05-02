function includeHeader() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', '../common/header.html', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('header').innerHTML = xhr.responseText;
		}
	};
	xhr.send();
}

function includeFooter() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', '../common/footer.html', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('footer').innerHTML = xhr.responseText;
		}
	};
	xhr.send();
}

includeHeader();
includeFooter();