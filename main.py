from browser import document, alert
import numpy

def echo(ev):
  alert(document["zone"].value)
  
document['mybutton'].bind('click',echo)
