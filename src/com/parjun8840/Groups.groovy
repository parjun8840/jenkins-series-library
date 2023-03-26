package com.parjun8840;

class Groups {
  int numberOfGroups
  def sre = []
  def devOps = []
  def script
  
  Car(script) {
    this.script = script
  }
  
    void printTeams() {
    script.echo "using Groups shared library from parjun8840 GitHub"
    script.echo "numberOfGroups: ${numberOfGroups}"      
    script.echo "SRE team members are: ${sre}"      
    script.echo "DevOps team members are:${devOps}"
    }  
  
}
