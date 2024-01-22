fun main() {
	var a = mutableListOf(4, 2, 3, 1)
	var pom = 0
	for (i in 0..<4) {
	for (j in i+1..<4) {
	if (a[i] > a[j]) {
	pom = a[i]
	a[i] = a[j]
	a[j] = pom
	}
	}
	}
	println("$a")
}
