function includeHeader() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://localhost:3200/common/header', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('header').innerHTML = xhr.responseText;
		}
	};
	xhr.send();
}

function includeFooter() {
	const xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://localhost:3200/common/footer', true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.querySelector('footer').innerHTML = xhr.responseText;
		}
	};
	xhr.send();
}

includeHeader();
includeFooter();