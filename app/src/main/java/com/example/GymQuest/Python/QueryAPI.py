import requests
import json

def query_API(API_KEY, AI_Prompt):
    ANTHROPIC_API_KEY = API_KEY
    API_URL = 'https://api.anthropic.com/v1/messages'

    headers = {
        'x-api-key': ANTHROPIC_API_KEY,
        'anthropic-version': '2023-06-01',
        'content-type': 'application/json',
    }

    data = {
        "model": "claude-3-opus-20240229",
        "max_tokens": 1024,
        "messages": [
            {"role": "user", "content": AI_Prompt}
        ]
    }

    response = requests.post(API_URL, headers=headers, json=data)

    if response.status_code == 200:
        return extract_text(response.json())
    else:
        return "Error:", response.status_code, response.text

def extract_text(json_string):
    # Parse the JSON string into a Python dictionary
    json_dict = json.loads(json.dumps(json_string))
    # Extract the 'content' list
    content_list = json_dict.get('content', [])
    # Extract the 'text' field from the first item in the 'content' list
    text = content_list[0].get('text', '') if content_list else ''

    return text