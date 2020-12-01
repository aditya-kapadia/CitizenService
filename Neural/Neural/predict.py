import cv2
import os
import sys
import glob
import argparse
import numpy as np
import tensorflow as tf
import _string

# First, pass the path of the image
dir_path = os.path.dirname(('C:/Users/Aditya Kapadia/PycharmProjects/Deepblue/'))
image_path = sys.argv[0]
filename = dir_path
image_size=160
num_channels=3
images = []
# Reading the image using OpenCV
image = cv2.imread(filename+'./images.1.jpg')
print(type(image))
# Resizing the image to our desired size and preprocessing will be done exactly as done during training
hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV) #convert it to hsv
h, s, v = cv2.split(hsv)
h+=10
s+=20
v+=90
hsv = cv2.merge((h, s, v))
image = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
kernel = np.array([[0,-1,0], [-1,5,-1], [0,-1,0]])
image = cv2.filter2D(image, -1, kernel)
image = cv2.resize(image, (image_size, image_size),0,0, cv2.INTER_LINEAR)
images.append(image)
print(type(images))

images = np.array(images, dtype=np.uint8)
images = images.astype('float32')
print(type(images))

image = np.multiply(image, 1.0/255.0)
#The input to the network is of shape [None image_size image_size num_channels]. Hence we reshape.
x_batch = image.reshape(1, image_size,image_size,num_channels)
# print(type(x_batch))

## Let us restore the saved model
sess = tf.Session()
# Step-1: Recreate the network graph. At this step only graph is created.
saver = tf.train.import_meta_graph('pothole-garbage-other-model.meta')
# Step-2: Now let's load the weights saved using the restore method.
saver.restore(sess, tf.train.latest_checkpoint('./'))

# Accessing the default graph which we have restored
graph = tf.get_default_graph()


# Now, let's get hold of the op that we can be processed to get the output.
# In the original network y_pred is the tensor that is the prediction of the network
y_pred = graph.get_tensor_by_name("y_pred:0")

## Let's feed the images to the input placeholders
x= graph.get_tensor_by_name("x:0")
print(type(x))

y_true = graph.get_tensor_by_name("y_true:0")
print(type(y_pred))
y_test_images = np.zeros((1, 3))


### Creating the feed_dict that is required to be fed to calculate y_pred
feed_dict_testing = {x: x_batch, y_true: y_test_images}
result=sess.run(y_pred, feed_dict=feed_dict_testing)

# for n in tf.get_default_graph().as_graph_def().node:
#      print(n.name)

# for op in graph.get_operations():
#     print(op.name())

# result is of this format
print(result)
pothole=result[0][0]
garbage=result[0][1]
other=result[0][2]
if (garbage>pothole and garbage >other) :
    print("Garbage")

elif (pothole>garbage and pothole>other):
    print("Pothole")

else :
    print("other")



