#!/bin/bash
API_URL="http://localhost:8080/api"

# Register
echo 'Registering user...'
http POST $API_URL/auth/register \
  name="Diego" email="diego@gmail.com" password="12345678"

# Login and capture the token
echo 'Logging in...'
LOGIN_RESPONSE=$(http POST $API_URL/auth/login \
  email="diego@gmail.com" password="12345678")

# Extract the token using jq
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token.token')
USER_ID=$(echo $LOGIN_RESPONSE | jq -r '.user.id')

echo "Token: $TOKEN"

# Get user profile using the token
echo 'Getting user profile...'
http -A bearer -a $TOKEN GET $API_URL/auth/profile

# Create post using the token
echo 'Creating post...'

http -A bearer -a $TOKEN POST $API_URL/posts \
  content="This is a test post" imgUrl="https://picsum.photos/200/300" 

  http -A bearer -a $TOKEN POST $API_URL/posts \
  content="This is a test post2" imgUrl="https://picsum.photos/200/300" 

echo "Getting post..."
POST_ID=($(http -A bearer -a $TOKEN GET $API_URL/posts | jq -r '.content | .[] | .id'))
echo "Post ID: $POST_ID"
# Create comment

http -A bearer -a $TOKEN POST $API_URL/comments \
  content="This is a test comment" postId=$POST_ID

http -A bearer -a $TOKEN POST $API_URL/comments \
  content="This another test comment" postId=$POST_ID

# Get comments
echo 'Getting comments...'
COMMENT_ID=($(http -A bearer -a $TOKEN GET $API_URL/posts/$POST_ID/comments | jq -r '.content | .[] | .id'))
echo "Comment ID: $COMMENT_ID"

# Update comment
http -A bearer -a $TOKEN PUT $API_URL/comments/$COMMENT_ID \
  content="This is a test comment updated"

# Get comments
echo 'Getting comments...'
http -A bearer -a $TOKEN GET $API_URL/posts/$POST_ID/comments

# Delete comment
echo 'Deleting comment...'
http -A bearer -a $TOKEN DELETE $API_URL/comments/$COMMENT_ID

# Get comments
echo 'Getting comments...'
http -A bearer -a $TOKEN GET $API_URL/posts/$POST_ID/comments

# Liking post
http -A bearer -a $TOKEN POST $API_URL/likes/posts/$POST_ID

# Get likes
echo 'Getting likes...'
http -A bearer -a $TOKEN GET $API_URL/likes/posts/$POST_ID

# Get likes by user
echo 'Getting likes by user...' 
echo "User ID: $USER_ID"
http -A bearer -a $TOKEN GET $API_URL/likes/users/$USER_ID

# Remove like
echo 'Removing like...'
http -A bearer -a $TOKEN DELETE $API_URL/likes/posts/$POST_ID

# Get post
http -A bearer -a $TOKEN GET $API_URL/posts/$POST_ID
http -A bearer -a $TOKEN GET $API_URL/posts/123
