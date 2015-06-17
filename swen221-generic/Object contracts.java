public boolean equals(Object o){
	if (o != null && o.getClass()==this.class){
		return data==((Par) o ).data;
	} else { return false; }
}

